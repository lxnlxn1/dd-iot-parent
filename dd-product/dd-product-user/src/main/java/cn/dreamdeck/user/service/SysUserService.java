package cn.dreamdeck.user.service;

import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.model.user.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
public interface SysUserService extends IService<SysUser> {

    SysUser login(SysUser sysUser);

    List<SysRole> getRole(String userId);

    SysUser getUser(String userId);
}
