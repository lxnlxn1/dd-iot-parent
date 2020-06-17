package cn.dreamdeck.iot.client;



import cn.dreamdeck.iot.client.impl.DdDeviceFeignFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(contextId = "dDeviceFeignService",fallback = DdDeviceFeignFeignClientImpl.class)
public interface DdDeviceFeignService {

}
