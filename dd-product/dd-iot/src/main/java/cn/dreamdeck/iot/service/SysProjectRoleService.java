package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdMenu;
import cn.dreamdeck.model.iot.SysProjectRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */
public interface SysProjectRoleService extends IService<SysProjectRole> {

    boolean saveRole(String projectId, String roleName, String menuIds);

    boolean delRole(String projectId, String roleId);


    List<DdMenu> getAllRoleByUserId(String projectId, String userId,String version);


    boolean updateMenuByRoleId(String projectId, String roleName, String menuIds);
}
