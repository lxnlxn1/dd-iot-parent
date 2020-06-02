package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdDeviceMeterWater;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceMeterWaterService extends IService<DdDeviceMeterWater> {

    /**
     * 水表list
     *
     * @param page
     * @param ddDeviceMeterWater
     * @return
     */
    IPage list(Page page, DdDeviceMeterWater ddDeviceMeterWater);

    /**
     * 添加水表
     *
     * @param ddDeviceMeterWater
     * @return
     */
    Boolean saveMeterWater(DdDeviceMeterWater ddDeviceMeterWater);

    /**
     * 修改水表
     *
     * @param ddDeviceMeterWater
     * @return
     */
    Boolean updateMeterWater(DdDeviceMeterWater ddDeviceMeterWater);

    /**
     * 删除水表
     *
     * @param ddDeviceMeterWater
     * @return
     */
    Boolean deleteById(DdDeviceMeterWater ddDeviceMeterWater);

}
