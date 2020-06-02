package cn.dreamdeck.iot.client;



import cn.dreamdeck.iot.client.impl.DdDeviceFeignFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import sun.security.util.SecurityConstants;

@FeignClient(contextId = "dDeviceFeignService",fallback = DdDeviceFeignFeignClientImpl.class)
public interface DdDeviceFeignService {

}
