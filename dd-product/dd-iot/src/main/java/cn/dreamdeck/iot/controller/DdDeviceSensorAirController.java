package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceSensorAirService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceSensorAir;
import cn.dreamdeck.model.iot.SysDeviceBrand;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("sensorAir")
@Api(value = "sensorAir", tags = "空气设备管理")
public class DdDeviceSensorAirController {

    private DdDeviceSensorAirService ddDeviceSensorAirService;

    private SysDeviceBrandService sysDeviceBrandService;

    private DdDeviceService ddDeviceService;

    /**
     * 空气列表
     */

    @ApiOperation(value = "土壤列表", notes = "土壤列表")

    @PostMapping("list")

    public DdResult list(Page page, DdDeviceSensorAir ddDeviceSensorAir) {

        return DdResult.ok(ddDeviceSensorAirService.list(page, ddDeviceSensorAir));
    }

    /**
     * 删除空气设备
     *
     * @param deviceId
     * @return
     */


    @ApiOperation(value = "删除空气设备", notes = "删除空气设备")

    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceSensorAir ddss = ddDeviceSensorAirService.getById(deviceId);
        if (ddss == null) {
            return DdResult.ok("找不到该设备");
        }
        ddDeviceSensorAirService.deleteById(ddss);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok("成功");
    }

    /**
     * 修改空气设备
     */
    @ApiOperation(value = "修改空气", notes = "修改空气")
    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceSensorAir ddDeviceSensorAir) {

        ddDeviceSensorAir.setStatus(0);
        ddDeviceSensorAir.setCreateTime(DateUtil.getTime());
        ddDeviceSensorAir.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceSensorAirService.updateAir(ddDeviceSensorAir);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceSensorAir.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceSensorAir.getDeviceId());
            dd.setProjectId(ddDeviceSensorAir.getProjectId());
            dd.setCreateTime(ddDeviceSensorAir.getCreateTime());
            dd.setDeviceName(ddDeviceSensorAir.getDeviceName());
            dd.setLatitude(ddDeviceSensorAir.getLatitude());
            dd.setLongitude(ddDeviceSensorAir.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceSensorAir.getUserId());
            dd.setStatus(ddDeviceSensorAir.getStatus());
            dd.setLastTime(ddDeviceSensorAir.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("修改成功");
        }
        return DdResult.fail("修改成功");
    }


    /**
     * 添加空气设备
     *
     * @param ddDeviceSensorAir
     * @return
     */

    @ApiOperation(value = "添加土壤", notes = "添加土壤")
    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceSensorAir ddDeviceSensorAir) {

        ddDeviceSensorAir.setStatus(0);
        ddDeviceSensorAir.setCreateTime(DateUtil.getTime());
        ddDeviceSensorAir.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceSensorAirService.saveAir(ddDeviceSensorAir);
        if (b == true) {

            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceSensorAir.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceSensorAir.getDeviceId());
            dd.setProjectId(ddDeviceSensorAir.getProjectId());
            dd.setCreateTime(ddDeviceSensorAir.getCreateTime());
            dd.setDeviceName(ddDeviceSensorAir.getDeviceName());
            dd.setLatitude(ddDeviceSensorAir.getLatitude());
            dd.setLongitude(ddDeviceSensorAir.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceSensorAir.getUserId());
            dd.setStatus(ddDeviceSensorAir.getStatus());
            dd.setLastTime(ddDeviceSensorAir.getLastTime());
            ddDeviceService.saveDevice(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }
}
