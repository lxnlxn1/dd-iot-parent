package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceTypeService;
import cn.dreamdeck.model.iot.SysDeviceType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备类别管理 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "设备2级分类")
@RestController
@RequestMapping("/iot/deviceType")
public class SysDeviceTypeController {


    @Autowired
    private SysDeviceTypeService sysDeviceTypeService;

    //设备1级分类查询
    @ApiOperation(value = "设备2级分类查询")
    @GetMapping(value = "/getAllTypeByClassifyId/{classifyId}")
    public DdResult getAllTypeByClassifyId(@PathVariable(value = "classifyId", required = true) String classifyId) {
        List<SysDeviceType> deviceTypeList = sysDeviceTypeService.list(new QueryWrapper<SysDeviceType>().eq("classify_id", classifyId).eq("status", 0));
        if (null != deviceTypeList && deviceTypeList.size() >= 0) {
            return DdResult.ok(deviceTypeList);
        }
        return DdResult.fail("查询2级分类失败");
    }


    //添加1级分类
    @ApiOperation(value = "添加2级分类(不开放)")
    @RequestMapping(value = "/saveTypeByClassifyId", method = RequestMethod.PUT)
    public DdResult saveBrand(@PathVariable(value = "classifyId", required = true) String classifyId, @RequestBody SysDeviceType sysDeviceType) {

        sysDeviceType.setClassifyId(Integer.valueOf(classifyId));
        sysDeviceType.setStatus(0);
        boolean save = sysDeviceTypeService.save(sysDeviceType);
        if (save) {
            return DdResult.ok("添加2级分类成功");
        }
        return DdResult.fail("添加2级分类失败");
    }


    //回显1级分类
    @ApiOperation(value = "回显2级分类查询(不开放)")
    @GetMapping("/getType/{typeId}")
    public DdResult getBrand(@PathVariable(value = "typeId", required = true) String typeId) {

        SysDeviceType deviceType = sysDeviceTypeService.getById(typeId);

        if (null != deviceType) {
            return DdResult.ok(deviceType);
        }

        return DdResult.fail("查询2级分类失败");
    }

    //更改1级分类
    @ApiOperation(value = "更改2级分类(不开放)")
    @PostMapping(value = "/updateType")
    public DdResult updateBrand(@RequestBody SysDeviceType sysDeviceType) {

        boolean update = sysDeviceTypeService.updateById(sysDeviceType);
        if (update) {
            return DdResult.ok("更改2级分类成功");
        }
        return DdResult.fail("更改2级分类失败");
    }

    //删除1级分类
    @ApiOperation(value = "删除2级分类(不开放)")
    @GetMapping("/delType/{typeId}")
    public DdResult delBrand(@PathVariable(value = "typeId", required = true) String typeId) {

        SysDeviceType deviceType = sysDeviceTypeService.getById(typeId);

        deviceType.setStatus(1);

        boolean update = sysDeviceTypeService.updateById(deviceType);

        if (update) {
            return DdResult.ok("删除2级分类成功");
        }
        return DdResult.fail("删除2级分类失败");
    }
}

