package cn.dreamdeck.user.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.client.DdIotFeignService;
import cn.dreamdeck.model.iot.DdSync;
import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.service.constant.RedisConst;
import cn.dreamdeck.user.service.SysRoleService;
import cn.dreamdeck.user.service.SysUserService;
import cn.dreamdeck.user.synchronization.Oa.AutoProjectByOa;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
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
@Api(description = "用户管理")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysRoleService sysRoleService;


    @Autowired
    private DdIotFeignService ddSynFeignService;

    @Autowired
    AuthContextHolder authContextHolder;

    /**
     * 登录
     */
    @PostMapping("/passport/login/{version}")
    public DdResult login(@PathVariable(value = "version",required = true) String version,@RequestBody SysUser sysUser) {
        SysUser info = sysUserService.login(sysUser);
        if (info != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            HashMap<String, Object> map = new HashMap<>();
            map.put("token", token);
            redisTemplate.opsForValue().set(RedisConst.USER_LOGIN_KEY_PREFIX + token, info.getUserId().toString()+","+version, RedisConst.USERKEY_TIMEOUT, TimeUnit.SECONDS);
            return DdResult.ok(map);
        } else {
            return DdResult.fail().message("用户名或密码错误");
        }
    }

    @GetMapping("/passport/info")
    public DdResult info(HttpServletRequest request) {
        String token = authContextHolder.getUserId(request);
        SysUser sysUser = sysUserService.getUser(token);
        List<SysRole> roleList = sysRoleService.getRoleByUserId(token);
        StringBuffer roleName = new StringBuffer();
        for (SysRole sysRole : roleList) {
            roleName.append(sysRole.getRoleName());
        }
        if (null == sysUser) {
            return DdResult.fail("登录信息失效或用户已停用");
        }
        Map<String, String> map = new HashMap<>();
        map.put("name", sysUser.getUsername());
        map.put("avatar", sysUser.getAvatar());
        map.put("roles", roleName.toString());
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
        String token = authContextHolder.getUserToken(request);
        redisTemplate.delete(RedisConst.USER_LOGIN_KEY_PREFIX + token);
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
            sysUserList = sysUserService.list(new QueryWrapper<SysUser>().orderByAsc("user_id").select("user_id", "username", "phone","avatar"));
            redisTemplate.opsForValue().set(RedisConst.PROJECT_USER_KEY_PREFIX, sysUserList, RedisConst.PROJECT_USER_TIMEOUT, TimeUnit.SECONDS);
        }
        return DdResult.ok(sysUserList);
    }

    @ApiOperation(value = "根据用户ids集合数组返回数据")
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

    //OA用户同步接口
    @ApiOperation("OA用户同步接口")
    @GetMapping("/synchronizationUserByOa")
    public DdResult synchronizationProjectByOa() {
        DdResult synchronizationUrl = ddSynFeignService.getSynchronizationUrl("1");
        ObjectMapper objectMapper = new ObjectMapper();
        DdSync ddSync = objectMapper.convertValue(synchronizationUrl.getData(), DdSync.class);
        AutoProjectByOa autoProjectByOa = new AutoProjectByOa();
        int mas = autoProjectByOa.synchronizationProjectByOa(sysUserService, ddSync.getUrl());
        if (mas != -1) {
            return DdResult.ok("更新成功" + "本次更新" + mas + "条");
        }
        return DdResult.fail("更新失败");
    }

    //OA用户同步接口
    @ApiOperation("根据用户OldUserId获取用户信息")
    @GetMapping("/getUserById/{OldUserId}")
    public SysUser getUserById(@PathVariable("OldUserId") String OldUserId) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("old_id",OldUserId));

        if (null != sysUser) {
            return  sysUser;
        }
        return new SysUser();
    }


}

