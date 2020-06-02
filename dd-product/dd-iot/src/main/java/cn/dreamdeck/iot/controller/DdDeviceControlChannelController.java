package cn.dreamdeck.iot.controller;



import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceControlChannelService;
import cn.dreamdeck.iot.service.DdDeviceGatewayComService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;

import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.DdDeviceControlChannel;


import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@AllArgsConstructor
@RequestMapping("channel")
@Api(value = "channel", tags = "通道控制器管理")
public class DdDeviceControlChannelController {

    private DdDeviceControlChannelService ddDeviceControlChannelService;

    private DdDeviceService ddDeviceService;

    private SysDeviceBrandService sysDeviceBrandService;

    private DdDeviceGatewayComService ddDeviceGatewayComService;

    /**
     * 通道控制器列表
     */

    @ApiOperation(value = "通道控制器列表", notes = "通道控制器列表")

    @PostMapping("list")

    public DdResult list(Page page, DdDeviceControlChannel ddDeviceControlChannel) {

        return DdResult.ok(ddDeviceControlChannelService.list(page, ddDeviceControlChannel));
    }


    /**
     * 删除通道控制器
     */


    @ApiOperation(value = "删除通道控制器", notes = "删除通道控制器")

    @PostMapping("delete/{deviceId}")

    public DdResult delete(@PathVariable String deviceId) {
        DdDeviceControlChannel dd = ddDeviceControlChannelService.getById(deviceId);
        ddDeviceControlChannelService.deleteById(dd);
        DdDevice ddDevice = ddDeviceService.getById(deviceId);
        ddDeviceService.delectDevice(ddDevice);
        return DdResult.ok();
    }


    /**
     * 通道控制器修改
     */

    @ApiOperation(value = "通道控制器修改", notes = "通道控制器修改")

    @PostMapping("/update")
    public DdResult update(@Valid @RequestBody DdDeviceControlChannel ddDeviceControlChannel) {

        ddDeviceControlChannel.setStatus(0);
        ddDeviceControlChannel.setCreateTime(DateUtil.getTime());
        ddDeviceControlChannel.setLastTime(DateUtil.getTime());
        String idList = ",";
        Boolean b = ddDeviceControlChannelService.updateChannel(ddDeviceControlChannel);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceControlChannel.getBrandId());
            DdDevice dd = ddDeviceService.getById(ddDeviceControlChannel.getDeviceId());

            dd.setDeviceId(ddDeviceControlChannel.getDeviceId());
            dd.setProjectId(ddDeviceControlChannel.getProjectId());
            dd.setCreateTime(ddDeviceControlChannel.getCreateTime());
            dd.setDeviceIp(ddDeviceControlChannel.getDeviceIp());
            dd.setDeviceName(ddDeviceControlChannel.getDeviceName());
            dd.setLatitude(ddDeviceControlChannel.getLatitude());
            dd.setLongitude(ddDeviceControlChannel.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceControlChannel.getUserId());
            dd.setStatus(ddDeviceControlChannel.getStatus());
            dd.setLastTime(ddDeviceControlChannel.getLastTime());
            ddDeviceService.updateDevice(dd);
            return DdResult.ok("修改成功");
        }
        return DdResult.fail("修改失败");
    }


    /**
     * 添加通道控制器
     *
     * @param ddDeviceControlChannel
     * @return
     */
    @ApiOperation(value = "添加通道控制器", notes = "添加通道控制器")
    @PostMapping("/save")
    public DdResult save(@Valid @RequestBody DdDeviceControlChannel ddDeviceControlChannel) {

        ddDeviceControlChannel.setStatus(0);
        ddDeviceControlChannel.setCreateTime(DateUtil.getTime());
        ddDeviceControlChannel.setLastTime(DateUtil.getTime());
        ddDeviceControlChannel.setDeviceId(String.valueOf(UUID.randomUUID()));
        Boolean b = ddDeviceControlChannelService.saveChannel(ddDeviceControlChannel);
        if (b == true) {
            SysDeviceBrand s = sysDeviceBrandService.selectByBrandId(ddDeviceControlChannel.getBrandId());
            DdDevice dd = new DdDevice();
            dd.setDeviceId(ddDeviceControlChannel.getDeviceId());
            dd.setProjectId(ddDeviceControlChannel.getProjectId());
            dd.setCreateTime(ddDeviceControlChannel.getCreateTime());
            dd.setDeviceIp(ddDeviceControlChannel.getDeviceIp());
            dd.setDeviceName(ddDeviceControlChannel.getDeviceName());
            dd.setLatitude(ddDeviceControlChannel.getLatitude());
            dd.setLongitude(ddDeviceControlChannel.getLongitude());
            dd.setClassifyId(s.getClassifyId());
            dd.setTypeId(s.getTypeId());
            dd.setUserId(ddDeviceControlChannel.getUserId());
            dd.setStatus(ddDeviceControlChannel.getStatus());
            dd.setLastTime(ddDeviceControlChannel.getLastTime());
            ddDeviceService.save(dd);
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }
}
