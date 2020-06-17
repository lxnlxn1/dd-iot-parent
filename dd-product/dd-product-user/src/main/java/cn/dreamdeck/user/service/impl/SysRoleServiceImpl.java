package cn.dreamdeck.user.service.impl;

import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.model.user.SysUserRole;
import cn.dreamdeck.user.mapper.SysRoleMapper;
import cn.dreamdeck.user.service.SysRoleService;
import cn.dreamdeck.user.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<SysRole> getRoleByUserId(String userId) {


        List<SysRole> roleList = new ArrayList<>();
        List<SysUserRole> userRoleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId));

        for (SysUserRole sysUserRole : userRoleList) {
            roleList = baseMapper.selectList(new QueryWrapper<SysRole>().eq("role_id", sysUserRole.getRoleId()));
            return roleList;
        }

        return roleList;
    }
}
