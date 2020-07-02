package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.device.gateway.DdDeviceGateway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxn
 * @since 2020-07-01
 */
public interface DdDeviceGatewayService extends IService<DdDeviceGateway> {

    IPage<DdDeviceGateway> getTeacherPageVo(String current, String limit, String projectId);

    boolean updateGateway(DdDeviceGateway ddDeviceGateway);

    boolean delGateway(String gatewayId);
}
