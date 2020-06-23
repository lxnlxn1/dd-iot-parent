package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 所有设备列表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "设备管理")
@RestController
@RequestMapping("/iot/device")
public class DdDeviceController {

    @Autowired
    private DdDeviceService ddDeviceService;

    //查询设备
    @ApiOperation(value = "根据项目id，查询查询设备（分页，查询）")
    @PostMapping("/getDevicePageVoByProjectId/{current}/{limit}/{projectId}")
    public DdResult getTeacherPageVo(@PathVariable(value = "current", required = false) String current, @PathVariable(value = "limit", required = false) String limit, @PathVariable(value = "projectId", required = true) String projectId, @RequestBody(required = false) DdDeviceVo ddDeviceVo) {

        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 5L;
        }
        Page<DdDevice> page = new Page<>(current1, limit1);

        QueryWrapper<DdDevice> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id", projectId);

        if (null!=ddDeviceVo){

            if (!StringUtils.isEmpty(ddDeviceVo.getClassifyId()))
                wrapper.eq("classify_id", ddDeviceVo.getClassifyId());

            if (!StringUtils.isEmpty(ddDeviceVo.getBrandId()))
                wrapper.eq("brand_id", ddDeviceVo.getBrandId());

            if (!StringUtils.isEmpty(ddDeviceVo.getDeviceType()))
                wrapper.eq("device_type", ddDeviceVo.getDeviceType());

            if (!StringUtils.isEmpty(ddDeviceVo.getDeviceIp()))
                wrapper.like("device_ip", ddDeviceVo.getDeviceIp());

            if (!StringUtils.isEmpty(ddDeviceVo.getStatus()))
                wrapper.eq("status", ddDeviceVo.getStatus());

            if (!StringUtils.isEmpty(ddDeviceVo.getLastTime()))
                wrapper.like("last_time", ddDeviceVo.getLastTime());

            if (!StringUtils.isEmpty(ddDeviceVo.getCreateTime()))
                wrapper.like("create_time", ddDeviceVo.getCreateTime());

            if (!StringUtils.isEmpty(ddDeviceVo.getCreateTime()))
                wrapper.like("install_user_name", ddDeviceVo.getInstallUserName());
        }



        wrapper.orderByDesc("create_time");

        IPage<DdDevice> projectIPage = ddDeviceService.page(page, wrapper);

        IPage<DdDeviceVo> projectIPageVo = ddDeviceService.pageDeviceVo(projectIPage);

        if (projectIPageVo.getRecords().size() > 0) {
            return DdResult.ok(projectIPageVo);
        }

        return DdResult.fail("查询数据为空");
    }

    //查询设备
    @ApiOperation(value = "根据设备id，查询设备详情")
    @PostMapping("/getDeviceVo/{deviceId}")
    public DdResult getDeviceVo(@PathVariable(value = "deviceId", required = true) String deviceId) {
        DdDeviceVo ddDeviceVo = ddDeviceService.getDeviceVo(deviceId);
        if (ddDeviceVo != null) {
            return DdResult.ok(ddDeviceVo);
        }
        return DdResult.fail("查询数据为空");
    }

    //添加设备
    @ApiOperation(value = "添加设备")
    @PostMapping("/saveDeviceVo")
    public DdResult saveDeviceVo(@RequestBody DdDevice ddDevice) {


        return DdResult.fail("添加设备失败");
    }


}

