package cn.dreamdeck.user.service.impl;


import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.user.mapper.SysUserMapper;
import cn.dreamdeck.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    // 调用mapper 层
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser login(SysUser sysUser) {

        String passwd = sysUser.getPassword();

        //密码进行加密解密 ???


        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("company_id", sysUser.getCompanyId());
        queryWrapper.eq("password", passwd);
        SysUser info = sysUserMapper.selectOne(queryWrapper);
        if (info != null) {
            return info;
        }
        return null;
    }
}
