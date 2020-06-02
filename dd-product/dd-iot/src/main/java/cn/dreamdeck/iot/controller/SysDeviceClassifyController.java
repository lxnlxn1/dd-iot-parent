package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceClassifyService;
import cn.dreamdeck.iot.service.SysDeviceTypeService;
import cn.dreamdeck.iot.service.SysDictDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("classify")
@Api(value = "classify", tags = "设备类型管理")
public class SysDeviceClassifyController {

    private SysDeviceClassifyService sysDeviceClassifyService;

    private SysDictDataService sysDictDataService;

    private SysDeviceTypeService sysDeviceTypeService;

    /**
     * 列表
     */
    @ApiOperation(value = "类型列表", notes = "分页列表")
    @GetMapping("/list")
    public DdResult getList() {
        return DdResult.ok(sysDeviceClassifyService.getClassifyList());
    }
}
