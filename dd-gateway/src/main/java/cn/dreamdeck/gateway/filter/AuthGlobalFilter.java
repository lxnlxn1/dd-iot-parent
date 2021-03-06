package cn.dreamdeck.gateway.filter;

import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.result.ResultCodeEnum;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author mqx
 * @date 2020/3/25 14:49
 */

public class AuthGlobalFilter implements GlobalFilter {

    @Value("${authUrls.url}")
    private String authUrls;

    @Autowired
    private RedisTemplate redisTemplate;

     // 引入一个路径匹配工具类
    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     *
     * @param exchange 能够获取到我们请求路径。
     * @param chain 过滤链
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 得到用户的请求路径
        String path = request.getURI().getPath();
        // path 不能为空！为空则不允许访问
//        if (!StringUtils.isEmpty(path)){
//            //  验证是否内部接口，/**/inner/** ,不能访问，没有权限访问
//            if (antPathMatcher.match("/*",path)){
//                // 如果匹配正确，说明是内部接口，给一个响应提示没有权限访问！
//                ServerHttpResponse response = exchange.getResponse();
//                // 返回 没有权限访问！
//                return out(response, ResultCodeEnum.PERMISSION);
//            }
//        }
        //  用户用户信息
//        String userId = getUserId(request);

        //预留以后权限控制

//        if (antPathMatcher.match("/api/**/auth/**",path)){
//            if (StringUtils.isEmpty(userId)){
//                // 如果匹配正确，说明是内部接口，给一个响应提示没有权限访问！
//                ServerHttpResponse response = exchange.getResponse();
//                // 返回 没有权限访问！
//                return out(response, ResultCodeEnum.LOGIN_AUTH);
//            }
//        }

        // 验证用户请求的控制器
        // authUrls
        // 在配置文件中配置了，访问的控制器条件

        //用户ID为空 重定向
//        if (StringUtils.isEmpty(userId)){
//            // 如果匹配正确，说明是内部接口，给一个响应提示没有权限访问！
//            ServerHttpResponse response = exchange.getResponse();
//            // 303 表示请求对赢的资源存在着另一个url ，应该重定向
//            response.setStatusCode(HttpStatus.SEE_OTHER);
//            // 重定向到登录页面
//            response.getHeaders().set(HttpHeaders.LOCATION,"http://127.0.0.1:8000/login.html?originUrl="+request.getURI());
//            // 表示设置完成
//            return response.setComplete();
//        }



//
//        if (!StringUtils.isEmpty(authUrls)){
//            // 循环判断
//            for (String authUrl : authUrls.split(",")) {
//                // 判断格式
//                // path 中存在{authUrls} 并且 用户Id为空的情况下 未登录
//                 if (path.indexOf(authUrl)!=-1 && StringUtils.isEmpty(userId)){
//                     // 如果匹配正确，说明是内部接口，给一个响应提示没有权限访问！
//                     ServerHttpResponse response = exchange.getResponse();
//                     // 303 表示请求对赢的资源存在着另一个url ，应该重定向
//                     response.setStatusCode(HttpStatus.SEE_OTHER);
//                     // 重定向到登录页面
//                     response.getHeaders().set(HttpHeaders.LOCATION,"http://127.0.0.1:6666/login.html?originUrl="+request.getURI());
//                     // 表示设置完成
//                     return response.setComplete();
//                }
//            }
//        }


        // 所有请求都是走的网关，用户一次登录完成之后，应该让网关，把用户登录的信息传递给其他业务模块。
        // 将userId，userTempId 都传递给后台
//        if (!StringUtils.isEmpty(userId)){
//            if (!StringUtils.isEmpty(userId)){
//                // 将userId 保存到header 中
//                request.mutate().header("userId",userId).build();
//            }
//            // 将现在的request 变成ServerWebExchange
//            ServerWebExchange build = exchange.mutate().request(request).build();
//            return chain.filter(build);
//        }
        // 返回总体
        return chain.filter(exchange);

    }
    // 获取用户Id
    private String getUserId(ServerHttpRequest request) {
        // 用户信息存在redis
        String token = "";
        // 在登录成功的时候，将token 分别放入了，cookie，header 中。
        List<String> tokenList = request.getHeaders().get("token");
        // 判断集合
        if (tokenList!=null && tokenList.size()>0){
            // token 在 header 中只有一个值。
            token = tokenList.get(0);
        }else {
            // 可能放在cookie 中。
            MultiValueMap<String, HttpCookie> cookies = request.getCookies();
            // 获取token 数据
            HttpCookie cookie = cookies.getFirst("token");
            // 从cookie 中获取到了token
            if (cookie!=null){
                token = URLDecoder.decode(cookie.getValue());
            }
        }
        // 判断
        if (!StringUtils.isEmpty(token)){
            // 拼接缓存中的key
            String userKey = "user:login:"+token;
            // 从缓存中获取数据
            String userId = (String) redisTemplate.opsForValue().get(userKey);
            // 返回用户Id
            return userId;
        }
        return null;
    }


    // 没有权限
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        // 返回用户没有权限 将用户信息封装到Result 对象
        DdResult<Object> result = DdResult.build(null, resultCodeEnum);
        // 将result 变成字节数组
        byte[] bytes = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        // 然后将信息输出到页面
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type","application/json;charset=UTF-8");

        return response.writeWith(Mono.just(wrap));
    }
}
