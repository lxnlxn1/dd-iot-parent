package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdDeviceSensorSoli;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdDeviceSensorSoliService extends IService<DdDeviceSensorSoli> {
    /**
     * 土壤list
     *
     * @param page
     * @param ddDeviceSensorSoli
     * @return
     */
    IPage list(Page page, DdDeviceSensorSoli ddDeviceSensorSoli);

    /**
     * 添加土壤
     *
     * @param ddDeviceSensorSoli
     * @return
     */
    Boolean saveSoli(DdDeviceSensorSoli ddDeviceSensorSoli);

    /**
     * 修改土壤
     *
     * @param ddDeviceSensorSoli
     * @return
     */
    Boolean updateSoli(DdDeviceSensorSoli ddDeviceSensorSoli);

    /**
     * 删除土壤
     *
     * @param ddDeviceSensorSoli
     * @return
     */
    Boolean deleteById(DdDeviceSensorSoli ddDeviceSensorSoli);

}
