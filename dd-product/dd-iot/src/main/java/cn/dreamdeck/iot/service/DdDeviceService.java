package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 所有设备列表 服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */


public interface DdDeviceService extends IService<DdDevice> {


    IPage<DdDeviceVo> pageDeviceVo(IPage<DdDevice> projectIPage);

    DdDeviceVo getDeviceVo(String deviceId);
}
