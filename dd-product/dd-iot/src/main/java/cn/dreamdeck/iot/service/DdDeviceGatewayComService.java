package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceGatewayCom;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceGatewayComService extends IService<DdDeviceGatewayCom> {

    /**
     * 添加网关端口
     *
     * @param ddDeviceGatewayCom
     * @return
     */
    Boolean saveGetway(DdDeviceGatewayCom ddDeviceGatewayCom);

    /**
     * 修改网关端口
     *
     * @param ddDeviceGatewayCom
     * @return
     */
    Boolean updateGetway(DdDeviceGatewayCom ddDeviceGatewayCom);
}
