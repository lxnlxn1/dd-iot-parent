package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceGetway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceGetWayService extends IService<DdDeviceGetway> {

    /**
     * 网关list
     *
     * @param page
     * @param ddDeviceGetway
     * @return
     */
    IPage list(Page page, DdDeviceGetway ddDeviceGetway);

    /**
     * 添加网关
     *
     * @param ddDeviceGetway
     * @return
     */
    Boolean saveGetway(DdDeviceGetway ddDeviceGetway);

    /**
     * 修改网关
     *
     * @param ddDeviceGetway
     * @return
     */
    Boolean updateGetway(DdDeviceGetway ddDeviceGetway);

    /**
     * 删除网关
     *
     * @param ddDeviceGetway
     * @return
     */
    Boolean deleteById(DdDeviceGetway ddDeviceGetway);

}
