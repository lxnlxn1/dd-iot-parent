package cn.dreamdeck.iot.client;



import cn.dreamdeck.iot.client.impl.DdDeviceFeignFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "dd-iot",contextId = "dDeviceFeignService",fallback = DdDeviceFeignFeignClientImpl.class)
public interface DdDeviceFeignService {

}
