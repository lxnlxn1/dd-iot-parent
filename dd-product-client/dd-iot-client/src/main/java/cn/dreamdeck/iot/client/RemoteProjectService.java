package cn.dreamdeck.iot.client;


import cn.dreamdeck.iot.client.impl.RemoteProjectFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author lxkui
 * @date 2019/10/4
 */
@FeignClient(value = "dd-iot",contextId = "remoteProjectService",fallback = RemoteProjectFeignClientImpl.class)

public interface RemoteProjectService {



}
