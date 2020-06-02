package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.SysDeviceModel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysDeviceModelService extends IService<SysDeviceModel> {

    /**
     * 模糊查询分页列表
     *
     * @param page
     * @param sysDeviceModel
     * @return
     */
    IPage getModelPage(Page page, SysDeviceModel sysDeviceModel);

    /**
     * 设备类型查询分页列表
     *
     * @param page
     * @param sysDeviceModel
     * @return
     */
    IPage getTypePage(Page page, SysDeviceModel sysDeviceModel);

    SysDeviceModel getModelId(String modelId);

    /**
     * 添加分组
     *
     * @param sysDeviceModel
     * @return
     */
    Boolean saveBrand(SysDeviceModel sysDeviceModel);

    /**
     * 修改分组
     *
     * @param sysDeviceModel
     * @return
     */
    Boolean editBrand(SysDeviceModel sysDeviceModel);

    /**
     * 删除分组
     *
     * @param sysDeviceModel
     * @return
     */
    Boolean deleteBrand(SysDeviceModel sysDeviceModel);

}
