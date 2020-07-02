package cn.dreamdeck.iot.client.impl;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.client.DdIotFeignService;
import cn.dreamdeck.model.iot.DeviceConfig.DeviceConfig;
import org.springframework.stereotype.Component;

@Component
public class DdIotFeignServiceImpl implements DdIotFeignService {
    @Override
    public DdResult saveDeviceVo(DeviceConfig deviceConfig) {
        return null;
    }

    @Override
    public DdResult getSynchronizationUrl(String id) {
        return null;
    }

    @Override
    public DdResult updateSynchronizationUrl(String id, String url) {
        return null;
    }
}
