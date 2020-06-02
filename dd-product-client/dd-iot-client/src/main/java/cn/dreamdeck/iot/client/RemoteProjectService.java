package cn.dreamdeck.iot.client;


import cn.dreamdeck.iot.client.impl.DdDeviceFeignFeignClientImpl;
import cn.dreamdeck.iot.client.impl.RemoteProjectFeignClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import sun.security.util.SecurityConstants;

import java.util.List;

/**
 * @author lxkui
 * @date 2019/10/4
 */
@FeignClient(contextId = "remoteProjectService",fallback = RemoteProjectFeignClientImpl.class)

public interface RemoteProjectService {



}
