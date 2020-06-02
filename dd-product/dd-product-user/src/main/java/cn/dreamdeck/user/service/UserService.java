package cn.dreamdeck.user.service;


import cn.dreamdeck.model.user.SysUser;

public interface UserService {

    /**
     * 登录方法
     *
     * @param
     * @return
     */
    SysUser login(SysUser sysUser);
}