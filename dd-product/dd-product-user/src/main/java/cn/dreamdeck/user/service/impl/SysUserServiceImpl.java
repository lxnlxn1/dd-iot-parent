package cn.dreamdeck.user.service.impl;

import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.model.user.SysUserRole;
import cn.dreamdeck.user.mapper.SysUserMapper;
import cn.dreamdeck.user.service.SysRoleService;
import cn.dreamdeck.user.service.SysUserRoleService;
import cn.dreamdeck.user.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public SysUser login(SysUser sysUser) {

        String username = sysUser.getUsername();

        String password = sysUser.getPassword();

        SysUser sysUser1 = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username",username).eq("password",password));
        return sysUser1;
    }

    @Override
    public  List<SysRole> getRole(String userId) {
        List<SysUserRole> roleList = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId));

        ArrayList<Integer> ids = new ArrayList<>();
        roleList.stream().forEach(f->{
          ids.add(f.getRoleId());
      });
        List<SysRole> sysRoles = (List)sysRoleService.listByIds(ids);


        return sysRoles;
    }

    @Override
    public SysUser getUser(String userId) {

        SysUser sysUser = baseMapper.selectOne(new QueryWrapper<SysUser>().eq("user_id", userId).eq("lock_flag", 1).eq("del_flag", 1));

        return sysUser;
    }
}
