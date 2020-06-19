package cn.dreamdeck.common.util;//package cn.dreamdeck.common.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.util.MultiValueMap;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.URLDecoder;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息类
 */
@Component
public class AuthContextHolder {


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取当前登录用户id
     *
     * @param request
     * @return
     */
    // 获取用户Id
    public String getUserId(HttpServletRequest request) {
        // 用户信息存在redis
        String token = "";
        // 在登录成功的时候，将token 分别放入了，cookie，header 中。
        String token1 = request.getHeader("token");
        // 判断集合
        if (token1 != null) {
            // token 在 header 中只有一个值。
            token = token1;
        } else {
            // 可能放在cookie 中。
            Cookie[] cookies = request.getCookies();
            // 获取token 数据
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        // 判断
        if (!StringUtils.isEmpty(token)) {
            // 拼接缓存中的key
//            String userKey = "user:login:" + token;
//            // 从缓存中获取数据
//
//
//            String userId = (String) redisTemplate.opsForValue().get(userKey);
//
//            System.out.println(userId);
            // 返回用户Id
            return token;
        }
        return null;
    }


}
