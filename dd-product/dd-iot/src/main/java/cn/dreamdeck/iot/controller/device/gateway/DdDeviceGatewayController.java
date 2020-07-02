package cn.dreamdeck.iot.controller.device.gateway;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceGatewayService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.device.gateway.DdDeviceGateway;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-07-01
 */

@Api(description = "网关管理")
@RestController
@RequestMapping("/iot/deviceGateway")
public class DdDeviceGatewayController {

    @Autowired
    private DdDeviceGatewayService ddDeviceGatewayService;


    //查询设备
    @ApiOperation(value = "根据项目id，查询查询网关设备（分页，不查询，需求需要在加）")
    @GetMapping("/getDeviceGatewayPageByProjectId/{current}/{limit}/{projectId}")
    public DdResult getDeviceGatewayPageByProjectId(@PathVariable(value = "current", required = false) String current, @PathVariable(value = "limit", required = false) String limit, @PathVariable(value = "projectId", required = true) String projectId) {

        IPage<DdDeviceGateway> ddDeviceGatewayIPage = ddDeviceGatewayService.getTeacherPageVo(current, limit, projectId);

        if (ddDeviceGatewayIPage.getRecords().size() > 0) {
            return DdResult.ok(ddDeviceGatewayIPage);
        }
        return DdResult.fail("查询数据为空");
    }


    //数据回显
    //查询设备
    @ApiOperation(value = "网关数据回显")
    @GetMapping("/getDeviceGatewayById/{gatewayId}")
    public DdResult getDeviceGatewayById(@PathVariable(value = "gatewayId", required = false) String gatewayId) {

        DdDeviceGateway byId = ddDeviceGatewayService.getById(gatewayId);
        if (null != byId) {
            return DdResult.ok(byId);
        }
        return DdResult.fail("查询数据为空");
    }

    //更改
    @ApiOperation(value = "更改网关")
    @PostMapping(value = "/updateGateway")
    public DdResult updateGateway(@RequestBody DdDeviceGateway ddDeviceGateway) {

        boolean update = ddDeviceGatewayService.updateGateway(ddDeviceGateway);
        if (update) {
            return DdResult.ok("更改网关成功");
        }
        return DdResult.fail("更改网关失败");
    }

    //删除品牌厂家
    @ApiOperation(value = "删除网关")
    @GetMapping("/delGateway/{gatewayId}")
    public DdResult delGateway(@PathVariable(value = "gatewayId", required = true) String gatewayId) {


        boolean del = ddDeviceGatewayService.delGateway(gatewayId);


        if (del) {
            return DdResult.ok("删除网关失败");
        }

        return DdResult.fail("删除网关失败");
    }


}

