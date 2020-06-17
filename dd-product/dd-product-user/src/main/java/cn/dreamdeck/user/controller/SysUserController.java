package cn.dreamdeck.user.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.service.constant.RedisConst;
import cn.dreamdeck.user.service.SysRoleService;
import cn.dreamdeck.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
@RestController
@RequestMapping("/user")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 登录
     */
    @PostMapping("/passport/login")
    public DdResult login(SysUser sysUser) {
        SysUser info = sysUserService.login(sysUser);
        if (info != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", "admin");
            redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + token, info.getUserId().toString(), RedisConst.USERKEY_TIMEOUT, TimeUnit.SECONDS);
            return DdResult.ok(map);
        } else {
            return DdResult.fail().message("用户名或密码错误");
        }
    }


    @GetMapping("/passport/info")
    public DdResult info(HttpServletRequest request) {
        String userId = AuthContextHolder.getUserId(request);
        SysUser sysUser = sysUserService.getUser(userId);

        List<SysRole> roleList = sysRoleService.getRoleByUserId(userId);
        StringBuffer roleName = null;
        for (SysRole sysRole : roleList) {
            roleName.append(sysRole.getRoleName() + "<br/>");
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", sysUser.getUsername());
        map.put("avatar", sysUser.getAvatar());
        map.put("roles", roleName);
        return DdResult.ok(map);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @GetMapping("/passport/logout")
    public DdResult logout(HttpServletRequest request) {
        redisTemplate.delete(RedisConst.USER_LOGIN_KEY_PREFIX + request.getHeader("token"));
        return DdResult.ok();
    }

    /**
     * 根据用户id查询权限
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询权限")
    @GetMapping("/query/{userId}")
    public DdResult query(@PathVariable("userId") String userId) {
        List<SysRole> list = sysUserService.getRole(userId);
        return DdResult.ok(list);
    }

    @ApiOperation(value = "返回所有人员Id与姓名/联系方式")
    @GetMapping("/getAll")
    public DdResult getAll() {
        List<SysUser> sysUserList = null;

        sysUserList = (List<SysUser>) redisTemplate.opsForValue().get(RedisConst.PROJECT_USER_KEY_PREFIX);
        if (sysUserList == null) {
            sysUserList = sysUserService.list(new QueryWrapper<SysUser>().orderByAsc("user_id").select("user_id", "username", "phone"));
            redisTemplate.opsForValue().set(RedisConst.PROJECT_USER_KEY_PREFIX, sysUserList, RedisConst.PROJECT_USER_TIMEOUT, TimeUnit.SECONDS);
        }


        return DdResult.ok(sysUserList);
    }

    @ApiOperation(value = "根据用户is集合数组返回数据")
    @GetMapping("/getUserByIds/{userIds}")
    List<SysUser> getUserByIds(@PathVariable("userIds") String userIds) {

        String[] split = userIds.split(",");
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            integers.add(Integer.valueOf(split[i]));
        }
        List<SysUser> sysUsers = (List<SysUser>) sysUserService.listByIds(integers);
        return sysUsers;
    }

    ;


}

