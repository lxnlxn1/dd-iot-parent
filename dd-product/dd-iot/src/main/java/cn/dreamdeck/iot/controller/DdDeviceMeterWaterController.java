package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceMeterWaterService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceMeterWater;
import cn.dreamdeck.model.iot.SysDeviceBrand;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("meterWater")
@Api(value = "meterWater", tags = "水表管理")
public class DdDeviceMeterWaterController {

    private DdDeviceMeterWaterService ddDeviceMeterWaterService;

    private DdDeviceService ddDeviceService;

    private SysDeviceBrandService sysDeviceBrandService;

    /**
     * 网关列表
     */

    @ApiOperation(value = "水表列表", notes = "水表列表")

    @PostMapping("list")

    public DdResult list(Page page, DdDeviceMeterWater ddDeviceMeterWater) {

        return DdResult.ok(ddDeviceMeterWaterService.list(page, ddDeviceMeterWater));
    }

    /**
     * 删除水表设备
     *
     * @param deviceId
     * @return
     */


    @ApiOperation(value = "删除水表设备", notes = "删除水表设备")

    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceMeterWater ddmw = ddDeviceMeterWaterService.getById(deviceId);
        if (ddmw == null) {
            return DdResult.ok("找不到该设备");
        }
        ddDeviceMeterWaterService.deleteById(ddmw);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok("成功");
    }

    /**
     * 修改土壤设备
     */

    @ApiOperation(value = "修改土壤", notes = "修改土壤")

    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceMeterWater ddDeviceMeterWater) {

        ddDeviceMeterWater.setStatus(0);
        ddDeviceMeterWater.setCreateTime(DateUtil.getTime());
        ddDeviceMeterWater.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceMeterWaterService.updateMeterWater(ddDeviceMeterWater);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceMeterWater.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceMeterWater.getDeviceId());
            dd.setProjectId(ddDeviceMeterWater.getProjectId());
            dd.setCreateTime(ddDeviceMeterWater.getCreateTime());
            dd.setDeviceName(ddDeviceMeterWater.getDeviceName());
            dd.setLatitude(ddDeviceMeterWater.getLatitude());
            dd.setLongitude(ddDeviceMeterWater.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceMeterWater.getUserId());
            dd.setStatus(ddDeviceMeterWater.getStatus());
            dd.setLastTime(ddDeviceMeterWater.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("修改成功");
        }
        return DdResult.fail("修改成功");
    }


    /**
     * 添加水表设备
     *
     * @param ddDeviceMeterWater
     * @return
     */

    @ApiOperation(value = "添加水表设备", notes = "添加水表设备")

    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceMeterWater ddDeviceMeterWater) {

        ddDeviceMeterWater.setStatus(0);
        ddDeviceMeterWater.setCreateTime(DateUtil.getTime());
        ddDeviceMeterWater.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceMeterWaterService.saveMeterWater(ddDeviceMeterWater);
        if (b == true) {

            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceMeterWater.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceMeterWater.getDeviceId());
            dd.setProjectId(ddDeviceMeterWater.getProjectId());
            dd.setCreateTime(ddDeviceMeterWater.getCreateTime());
            dd.setDeviceName(ddDeviceMeterWater.getDeviceName());
            dd.setLatitude(ddDeviceMeterWater.getLatitude());
            dd.setLongitude(ddDeviceMeterWater.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceMeterWater.getUserId());
            dd.setStatus(ddDeviceMeterWater.getStatus());
            dd.setLastTime(ddDeviceMeterWater.getLastTime());
            ddDeviceService.saveDevice(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }

}
