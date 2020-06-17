package cn.dreamdeck.user.service;

import cn.dreamdeck.model.user.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRoleByUserId(String userId);
}
