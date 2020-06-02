package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.SysDeviceModel;
import cn.dreamdeck.model.iot.SysDictData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("sysDevice")
@Api(value = "sysDevice", tags = "设备管理")
public class SysDeviceModelController {

    private SysDeviceModelService sysDeviceModelService;

    private SysDictDataService sysDictDataService;

    private SysDeviceBrandService sysDeviceBrandService;


    /**
     * 模糊搜索设备列表分页
     *
     * @param page
     * @param sysDeviceModel
     * @return
     */
    @ApiOperation(value = "模糊搜索设备列表", notes = "分页列表")
    @PostMapping("/page")
    public DdResult getLikePage(Page page, SysDeviceModel sysDeviceModel) {
        return DdResult.ok(sysDeviceModelService.getModelPage(page, sysDeviceModel));
    }

    /**
     * 设备类型筛选列表分页
     *
     * @param page
     * @param sysDeviceModel
     * @return
     */
    @ApiOperation(value = "设备类型筛选", notes = "分页列表")
    @PostMapping("/typePage")
    public DdResult getDeviceTypePage(Page page, SysDeviceModel sysDeviceModel) {
        return DdResult.ok(sysDeviceModelService.getTypePage(page, sysDeviceModel));
    }

    /**
     * 根据设备类型id查询设备详情
     */
    @ApiOperation(value = "根据id查询", notes = "根据id查询")
    @PostMapping("/modelId")
    public DdResult getModelId(@Valid String modelId) {
        SysDeviceModel sysDeviceModel = sysDeviceModelService.getModelId(modelId);
        SysDeviceBrand sysDeviceBrand = sysDeviceBrandService.getById(sysDeviceModel.getBrandId());
        String result = "";
        if (sysDeviceModel.getDateList() != null) {
            String[] list = sysDeviceModel.getDateList().split(",");

            for (int i = 0; i < list.length; i++) {
                SysDictData sd = sysDictDataService.selectById(list[i]);
                if (sd != null) {
                    result += sd.getDictLabel() + ",";
                }


            }

        }
        sysDeviceModel.setBrandId(sysDeviceBrand.getBrandName());
        sysDeviceModel.setDateList(result);

        return DdResult.ok(sysDeviceModel);
    }
}
