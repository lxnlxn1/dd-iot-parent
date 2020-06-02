package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface SysDeviceBrandService extends IService<SysDeviceBrand> {

    /**
     * 分页列表
     *
     * @param page
     * @param sysDeviceBrand
     * @return
     */
    IPage getBrandPage(Page page, SysDeviceBrand sysDeviceBrand);

    SysDeviceBrand selectByBrandId(String brandId);

    SysDeviceBrand selectById(String brandId);


    /**
     * 添加分组
     *
     * @param sysDeviceBrand
     * @return
     */
    Boolean saveBrand(SysDeviceBrand sysDeviceBrand);

    /**
     * 修改分组
     *
     * @param sysDeviceBrand
     * @return
     */
    Boolean editBrand(SysDeviceBrand sysDeviceBrand);

    /**
     * 删除分组
     *
     * @param sysDeviceBrand
     * @return
     */
    Boolean deleteBrand(SysDeviceBrand sysDeviceBrand);

}
