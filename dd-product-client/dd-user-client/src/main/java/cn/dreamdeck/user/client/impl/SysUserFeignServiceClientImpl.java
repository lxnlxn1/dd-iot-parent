package cn.dreamdeck.user.client.impl;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.user.client.SysUserFeignService;
import org.springframework.stereotype.Component;

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
}
