package cn.dreamdeck.user.service.impl;


import cn.dreamdeck.model.user.SysUserRole;
import cn.dreamdeck.user.mapper.SysUserRoleMapper;
import cn.dreamdeck.user.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}
