package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.vo.DdDeviceInfoVo;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceService extends IService<DdDevice> {

    /**
     * 添加设备
     *
     * @param ddDevice
     * @return
     */
    Boolean saveDevice(DdDevice ddDevice);

    Boolean updateDevice(DdDevice ddDevice);

    Boolean delectDevice(DdDevice ddDevice);

    IPage<DdDeviceVo> getDeviceList(Page page, Integer projectId, String classifyId);

    IPage<DdDeviceVo> getTotalDeviceList(Page page);


    DdDeviceInfoVo deviceInfo(String deviceId);

    DdDevice selectIpAndModelId(String ip, String modelId);
}
