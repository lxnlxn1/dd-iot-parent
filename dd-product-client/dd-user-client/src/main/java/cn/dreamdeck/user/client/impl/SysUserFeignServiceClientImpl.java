package cn.dreamdeck.user.client.impl;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.user.client.SysUserFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SysUserFeignServiceClientImpl implements SysUserFeignService {
    @Override
    public DdResult query(String userId) {


       // return DdResult.fail().message("链接失败");
        throw new RuntimeException();
    }

    @Override
    public DdResult getAll() {
        return null;
    }

    @Override
    public List<SysUser> getUserByIds(String userIds) {
        return null;
    }

    @Override
    public SysUser getUserById(String userId) {
        return null;
    }
}
