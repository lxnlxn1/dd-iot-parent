package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceGatewayComService;
import cn.dreamdeck.iot.service.DdDeviceGetWayService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceGetway;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.vo.DdDeviceGetwayModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("getway")
@Api(value = "getway", tags = "网关管理")
public class DdDeviceGetwayController {
    private DdDeviceGetWayService ddDeviceGetWayService;

    private DdDeviceService ddDeviceService;

    private SysDeviceBrandService sysDeviceBrandService;

    private DdDeviceGatewayComService ddDeviceGatewayComService;

    /**
     * 网关列表
     */

    @ApiOperation(value = "网关list", notes = "网关list")

    @PostMapping("list")

    public DdResult list(Page page, DdDeviceGetway ddDeviceGetway) {

        return DdResult.ok(ddDeviceGetWayService.list(page, ddDeviceGetway));
    }


    /**
     * 网关删除
     */


    @ApiOperation(value = "删除网关", notes = "删除网关")

    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceGetway dd = ddDeviceGetWayService.getById(deviceId);
        ddDeviceGetWayService.deleteById(dd);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok();
    }


    /**
     * 网关修改
     */

    @ApiOperation(value = "修改网关", notes = "修改网关")

    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceGetwayModel ddDeviceGetway) {

        ddDeviceGetway.setStatus(0);
        ddDeviceGetway.setCreateTime(DateUtil.getTime());
        ddDeviceGetway.setLastTime(DateUtil.getTime());
        ddDeviceGetway.setDeviceNetworkType(1);
        String idList = ",";
        if (ddDeviceGetway.getList() != null) {
            for (int i = 0; i < ddDeviceGetway.getList().size(); i++) {
                ddDeviceGatewayComService.updateGetway(ddDeviceGetway.getList().get(i));
            }
        }
        Boolean b = ddDeviceGetWayService.updateGetway(ddDeviceGetway);
        QueryWrapper<SysDeviceBrand> wrapper = new QueryWrapper<SysDeviceBrand>();
        wrapper.eq("brand_name",ddDeviceGetway.getBrandId());
        SysDeviceBrand brand = sysDeviceBrandService.getOne(wrapper);
        if (b == true) {
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceGetway.getDeviceId());
            dd.setProjectId(ddDeviceGetway.getProjectId());
            dd.setCreateTime(ddDeviceGetway.getCreateTime());
            dd.setDeviceIp(ddDeviceGetway.getDeviceIp());
            dd.setDeviceName(ddDeviceGetway.getDeviceName());
            dd.setLatitude(ddDeviceGetway.getLatitude());
            dd.setLongitude(ddDeviceGetway.getLongitude());
            dd.setClassifyId(brand.getClassifyId());
            dd.setTypeId(brand.getTypeId());
            dd.setUserId(ddDeviceGetway.getUserId());
            dd.setStatus(ddDeviceGetway.getStatus());
            dd.setLastTime(ddDeviceGetway.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("修改成功");
        }
        return DdResult.fail("修改失败");
    }


    /**
     * 添加分组
     *
     * @param ddDeviceGetway
     * @return
     */

    @ApiOperation(value = "添加网关", notes = "添加网关")

    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceGetwayModel ddDeviceGetway) {

        ddDeviceGetway.setStatus(0);
        ddDeviceGetway.setCreateTime(DateUtil.getTime());
        ddDeviceGetway.setLastTime(DateUtil.getTime());
        ddDeviceGetway.setDeviceNetworkType(1);
        String idList = ",";
        if (ddDeviceGetway.getList() != null) {
            for (int i = 0; i < ddDeviceGetway.getList().size(); i++) {
                ddDeviceGetway.getList().get(i).setGatewayComId(String.valueOf(UUID.randomUUID()));
                idList += ddDeviceGetway.getList().get(i).getGatewayComId() + ",";
                ddDeviceGetway.getList().get(i).setDdGeteWayId(ddDeviceGetway.getDeviceId());
                ddDeviceGatewayComService.saveGetway(ddDeviceGetway.getList().get(i));
            }
        }

        QueryWrapper<SysDeviceBrand> wrapper = new QueryWrapper<SysDeviceBrand>();
        wrapper.eq("brand_name",ddDeviceGetway.getBrandId());
        SysDeviceBrand brand = sysDeviceBrandService.getOne(wrapper);
        ddDeviceGetway.setDeviceComList(idList);
        ddDeviceGetway.setBrandId(brand.getBrandId());
        Boolean b = ddDeviceGetWayService.saveGetway(ddDeviceGetway);
        if (b == true) {
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceGetway.getDeviceId());
            dd.setModelId(ddDeviceGetway.getModelId());
            dd.setBrandId(brand.getBrandId());
            dd.setProjectId(ddDeviceGetway.getProjectId());
            dd.setCreateTime(ddDeviceGetway.getCreateTime());
            dd.setDeviceIp(ddDeviceGetway.getDeviceIp());
            dd.setDeviceName(ddDeviceGetway.getDeviceName());
            dd.setLatitude(ddDeviceGetway.getLatitude());
            dd.setLongitude(ddDeviceGetway.getLongitude());
            dd.setClassifyId(brand.getClassifyId());
            dd.setTypeId(brand.getTypeId());
            dd.setUserId(ddDeviceGetway.getUserId());
            dd.setStatus(ddDeviceGetway.getStatus());
            dd.setLastTime(ddDeviceGetway.getLastTime());
            ddDeviceService.save(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }
}
