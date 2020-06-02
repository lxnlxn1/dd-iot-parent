package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceSensorAir;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceSensorAirService extends IService<DdDeviceSensorAir> {

    /**
     * 空气list
     *
     * @param page
     * @param ddDeviceSensorAir
     * @return
     */
    IPage list(Page page, DdDeviceSensorAir ddDeviceSensorAir);

    /**
     * 添加空气
     *
     * @param ddDeviceSensorAir
     * @return
     */
    Boolean saveAir(DdDeviceSensorAir ddDeviceSensorAir);

    /**
     * 修改空气
     *
     * @param ddDeviceSensorAir
     * @return
     */
    Boolean updateAir(DdDeviceSensorAir ddDeviceSensorAir);

    /**
     * 删除空气
     *
     * @param ddDeviceSensorAir
     * @return
     */
    Boolean deleteById(DdDeviceSensorAir ddDeviceSensorAir);

}
