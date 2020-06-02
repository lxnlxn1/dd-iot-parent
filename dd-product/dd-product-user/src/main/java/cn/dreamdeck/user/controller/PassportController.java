package cn.dreamdeck.user.controller;

import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.service.constant.RedisConst;
import cn.dreamdeck.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户认证接口
 * </p>
 */
@RestController
@RequestMapping("/user/passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 登录
     *
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/login")
    public DdResult login(SysUser sysUser) {
        SysUser info = userService.login(sysUser);


        System.out.println("-----------------------------------");
        if (info != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", info.getUsername());
            map.put("avatar", info.getAvatar());
            map.put("token", token);
            redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + token, info.getUserId().toString(), RedisConst.USERKEY_TIMEOUT, TimeUnit.SECONDS);
            return DdResult.ok(map);
        } else {
            return DdResult.fail().message("用户名或密码错误");
        }
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("logout")
    public DdResult logout(HttpServletRequest request) {
        redisTemplate.delete(RedisConst.USER_LOGIN_KEY_PREFIX + request.getHeader("token"));
        return DdResult.ok();
    }
}