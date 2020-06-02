package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceSensorWater;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceSensorWaterService extends IService<DdDeviceSensorWater> {

    /**
     * 水质list
     *
     * @param page
     * @param ddDeviceSensorWater
     * @return
     */
    IPage list(Page page, DdDeviceSensorWater ddDeviceSensorWater);

    /**
     * 添加水质设备
     *
     * @param ddDeviceSensorWater
     * @return
     */
    Boolean saveWater(DdDeviceSensorWater ddDeviceSensorWater);

    /**
     * 修改水质设备
     *
     * @param ddDeviceSensorWater
     * @return
     */
    Boolean updateWater(DdDeviceSensorWater ddDeviceSensorWater);

    /**
     * 删除水质设备
     *
     * @param ddDeviceSensorWater
     * @return
     */
    Boolean deleteById(DdDeviceSensorWater ddDeviceSensorWater);
}
