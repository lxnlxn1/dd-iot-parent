package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceGetWayService;
import cn.dreamdeck.iot.service.DdDeviceSensorSoliService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceGetway;
import cn.dreamdeck.model.iot.DdDeviceSensorSoli;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("sensorSoli")
@Api(value = "sensorSoli", tags = "土壤设备管理")
public class DdDeviceSensorSoliCotroller {

    private DdDeviceSensorSoliService ddDeviceSensorSoliService;

    private SysDeviceBrandService sysDeviceBrandService;

    private DdDeviceService ddDeviceService;

    private DdDeviceGetWayService ddDeviceGetWayService;


    /**
     * 土壤列表
     */

    @ApiOperation(value = "土壤列表", notes = "土壤列表")

    @PostMapping("list")

    public DdResult list(Page page, DdDeviceSensorSoli ddDeviceSensorSoli) {

        return DdResult.ok(ddDeviceSensorSoliService.list(page, ddDeviceSensorSoli));
    }


    /**
     * 删除土壤设备
     *
     * @param deviceId
     * @return
     */


    @ApiOperation(value = "删除土壤设备", notes = "删除土壤设备")
    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceSensorSoli ddss = ddDeviceSensorSoliService.getById(deviceId);
        if (ddss == null) {
            return DdResult.ok("找不到该设备");
        }
        ddDeviceSensorSoliService.deleteById(ddss);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok("成功");
    }

    /**
     * 修改土壤设备
     */
    @ApiOperation(value = "修改土壤", notes = "修改土壤")
    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceSensorSoli ddDeviceSensorSoli) {

        ddDeviceSensorSoli.setStatus(0);
        ddDeviceSensorSoli.setCreateTime(DateUtil.getTime());
        ddDeviceSensorSoli.setLastTime(DateUtil.getTime());
        Boolean b = ddDeviceSensorSoliService.updateSoli(ddDeviceSensorSoli);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceSensorSoli.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceSensorSoli.getDeviceId());
            dd.setProjectId(ddDeviceSensorSoli.getProjectId());
            dd.setModelId(ddDeviceSensorSoli.getModelId());
            dd.setBrandId(ddDeviceSensorSoli.getBrandId());
            dd.setCreateTime(ddDeviceSensorSoli.getCreateTime());
            dd.setDeviceName(ddDeviceSensorSoli.getDeviceName());
            dd.setLatitude(ddDeviceSensorSoli.getLatitude());
            dd.setSubsetId(ddDeviceSensorSoli.getSubsetId());
            dd.setLongitude(ddDeviceSensorSoli.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceSensorSoli.getUserId());
            dd.setStatus(ddDeviceSensorSoli.getStatus());
            dd.setLastTime(ddDeviceSensorSoli.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("修改成功");
        }
        return DdResult.fail("修改失败");
    }


    /**
     * 添加土壤设备
     *
     * @param ddDeviceSensorSoli
     * @return
     */
    @ApiOperation(value = "添加土壤", notes = "添加土壤")
    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceSensorSoli ddDeviceSensorSoli) {

        ddDeviceSensorSoli.setStatus(0);
        ddDeviceSensorSoli.setCreateTime(DateUtil.getTime());
        ddDeviceSensorSoli.setLastTime(DateUtil.getTime());

        QueryWrapper<DdDeviceGetway> wrapper = new QueryWrapper<>();
        wrapper.eq("device_id",ddDeviceSensorSoli.getGatewayId());
        DdDeviceGetway ddDeviceGetway = ddDeviceGetWayService.getOne(wrapper);
        if (ddDeviceGetway == null) {
            return DdResult.ok("网关未找到");
        }
//
////        QueryWrapper<DdDeviceGetway> wrapper1 = new QueryWrapper<>();
////        wrapper.eq("brand_id",ddDeviceSensorSoli.getBrandId());
////        SysDeviceBrand brand = sysDeviceBrandService.getOne(wrapper1);
//        ddDeviceSensorSoli.setGatewayId(ddDeviceGetway.getDeviceId());
//        ddDeviceSensorSoli.setBrandId(brand.getBrandId());
//        Boolean b = ddDeviceSensorSoliService.saveSoli(ddDeviceSensorSoli);
//        if (b == true) {
//
//            DdDevice dd = new DdDevice();
//            dd.setDeviceId(ddDeviceSensorSoli.getDeviceId());
//            dd.setProjectId(ddDeviceSensorSoli.getProjectId());
//            dd.setCreateTime(ddDeviceSensorSoli.getCreateTime());
//            dd.setDeviceName(ddDeviceSensorSoli.getDeviceName());
//            dd.setLatitude(ddDeviceSensorSoli.getLatitude());
//            dd.setLongitude(ddDeviceSensorSoli.getLongitude());
//            dd.setClassifyId(brand.getClassifyId());
//            dd.setConnectBaud(ddDeviceSensorSoli.getConnectBaud());
//            dd.setTypeId(brand.getTypeId());
//            dd.setUserId(ddDeviceSensorSoli.getUserId());
//            dd.setStatus(ddDeviceSensorSoli.getStatus());
//            dd.setLastTime(ddDeviceSensorSoli.getLastTime());
//            ddDeviceService.saveDevice(dd);
//            return DdResult.ok("添加成功");
//        }
        return DdResult.fail("添加失败");
    }
}
