package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceSensorWaterService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceSensorWater;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("sensorWater")
@Api(value = "sensorWater", tags = "水质设备管理")
public class DdDeviceSensorWaterController {

    private DdDeviceSensorWaterService ddDeviceSensorWaterService;

    private SysDeviceBrandService sysDeviceBrandService;

    private DdDeviceService ddDeviceService;


    /**
     * 水质列表
     */
    @ApiOperation(value = "水质列表", notes = "水质列表")
    @PostMapping("list")

    public DdResult list(Page page, DdDeviceSensorWater ddDeviceSensorWater) {

        return DdResult.ok(ddDeviceSensorWaterService.list(page, ddDeviceSensorWater));
    }


    /**
     * 删除水质设备
     *
     * @param deviceId
     * @return
     */

    @ApiOperation(value = "删除水质设备", notes = "删除水质设备")
    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceSensorWater ddsw = ddDeviceSensorWaterService.getById(deviceId);
        if (ddsw == null) {
            return DdResult.ok("找不到该设备");
        }
        ddDeviceSensorWaterService.deleteById(ddsw);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok("成功");
    }

    /**
     * 修改水质设备
     */
    @ApiOperation(value = "修改水质设备", notes = "修改水质设备")
    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceSensorWater ddDeviceSensorWater) {

        ddDeviceSensorWater.setStatus(0);
        ddDeviceSensorWater.setCreateTime(DateUtil.getTime());
        ddDeviceSensorWater.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceSensorWaterService.updateWater(ddDeviceSensorWater);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceSensorWater.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceSensorWater.getDeviceId());
            dd.setProjectId(ddDeviceSensorWater.getProjectId());
            dd.setCreateTime(ddDeviceSensorWater.getCreateTime());
            dd.setDeviceName(ddDeviceSensorWater.getDeviceName());
            dd.setLatitude(ddDeviceSensorWater.getLatitude());
            dd.setLongitude(ddDeviceSensorWater.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceSensorWater.getUserId());
            dd.setStatus(ddDeviceSensorWater.getStatus());
            dd.setLastTime(ddDeviceSensorWater.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }


    /**
     * 添加水质
     *
     * @param ddDeviceSensorWater
     * @return
     */
    @ApiOperation(value = "添加水质", notes = "添加水质")
    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceSensorWater ddDeviceSensorWater) {

        ddDeviceSensorWater.setStatus(0);
        ddDeviceSensorWater.setCreateTime(DateUtil.getTime());
        ddDeviceSensorWater.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceSensorWaterService.saveWater(ddDeviceSensorWater);
        if (b == true) {

            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceSensorWater.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceSensorWater.getDeviceId());
            dd.setProjectId(ddDeviceSensorWater.getProjectId());
            dd.setCreateTime(ddDeviceSensorWater.getCreateTime());
            dd.setDeviceName(ddDeviceSensorWater.getDeviceName());
            dd.setLatitude(ddDeviceSensorWater.getLatitude());
            dd.setLongitude(ddDeviceSensorWater.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceSensorWater.getUserId());
            dd.setStatus(ddDeviceSensorWater.getStatus());
            dd.setLastTime(ddDeviceSensorWater.getLastTime());
            ddDeviceService.saveDevice(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }
}
