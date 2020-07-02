package cn.dreamdeck.iot.client;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.client.impl.DdIotFeignServiceImpl;
import cn.dreamdeck.model.iot.DeviceConfig.DeviceConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lxkui
 * @date 2019/10/4
 */
@FeignClient(value = "dd-iot", fallback = DdIotFeignServiceImpl.class)

public interface DdIotFeignService {



    //添加设备
    @ApiOperation(value = "添加设备")
    @PostMapping(value = "/iot/device/saveDeviceVo")
   DdResult saveDeviceVo(@RequestBody DeviceConfig deviceConfig);



    @ApiOperation("同步接口数据回显接口")
    @GetMapping("/iot/sync/getSynchronizationUrl/{id}")
     DdResult getSynchronizationUrl(@PathVariable("id") String id);

    @ApiOperation("更新项目同步接口")
    @GetMapping("/iot/sync/updateSynchronizationUrl/{id}/{url}")
    DdResult updateSynchronizationUrl(@PathVariable("id") String id,@PathVariable("url") String url);




}
