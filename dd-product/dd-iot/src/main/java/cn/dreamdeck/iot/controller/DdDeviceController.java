package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDictData;
import cn.dreamdeck.model.iot.vo.DdDeviceInfoVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("device")
@Api(value = "device", tags = "设备管理")
public class DdDeviceController {
    @Autowired
    private DdDeviceService ddDeviceService;

    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 所有设备信息
     */
    @ApiOperation(value = "所有设备信息", notes = "所有设备信息")
    @PostMapping("/totalList")
    public DdResult totalList(Page page) {
        return DdResult.ok(ddDeviceService.getTotalDeviceList(page));
    }


    /**
     * 根据设备类型查询设备属性
     */

    @ApiOperation(value = "设备列表", notes = "设备列表")
    @PostMapping("/list")

    public DdResult list(Page page, Integer projectId, String classifyId) {
        if (projectId == null) {
            return DdResult.fail("项目id不能为空");
        }
        return DdResult.ok(ddDeviceService.getDeviceList(page, projectId, classifyId));
    }

    /**
     * 根据设备id查询设备详情
     */
    @ApiOperation(value = "设备详情", notes = "设备详情")
    @PostMapping("/deviceInfo")
    public DdResult deviceInfo(String deviceId) {
        DdDeviceInfoVo ddDeviceInfoVo = ddDeviceService.deviceInfo(deviceId);
        String[] dataList = ddDeviceInfoVo.getDataList().split(",");
        String data = "";
        for (int i = 0; i < dataList.length; i++) {
            SysDictData sysDictData = sysDictDataService.getById(dataList[i]);
            data += sysDictData.getDictLabel() + ",";
        }
        ddDeviceInfoVo.setDataList(data);
        return DdResult.ok(ddDeviceInfoVo);
    }


    /**
     * 根据设备id查询设备详情
     */
    @ApiOperation(value = "根据Ip与型号是否有设备", notes = "设备详情")
    @GetMapping("/selectIpAndModelId/{id}")
    public DdResult selectIpAndModelId(@PathVariable(value = "id") String id) {

        System.out.println("---------------"+id);
       //DdDevice ddDevice = ddDeviceService.selectIpAndModelId(ip, modelId);
        return DdResult.ok(Boolean.TRUE);

    }


}
