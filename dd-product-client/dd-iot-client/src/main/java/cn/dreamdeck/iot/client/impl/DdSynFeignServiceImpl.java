package cn.dreamdeck.iot.client.impl;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.client.DdSynFeignService;
import org.springframework.stereotype.Component;

@Component
public class DdSynFeignServiceImpl implements DdSynFeignService {
    @Override
    public DdResult getSynchronizationUrl(String id) {
        return null;
    }

    @Override
    public DdResult updateSynchronizationUrl(String id, String url) {
        return null;
    }
}
