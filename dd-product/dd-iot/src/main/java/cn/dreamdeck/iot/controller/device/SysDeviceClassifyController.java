package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceClassifyService;
import cn.dreamdeck.model.iot.SysDeviceClassify;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 设备分类管理 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "设备1级分类")
@RestController
@RequestMapping("/iot/deviceClassify")
public class SysDeviceClassifyController {

    @Autowired
    private SysDeviceClassifyService sysDeviceClassifyService;


    //设备1级分类查询
    @ApiOperation(value = "设备1级分类查询")
    @GetMapping(value = "/getAllClassify")
    public DdResult getAllBrand() {
        List<SysDeviceClassify> list = sysDeviceClassifyService.list(new QueryWrapper<SysDeviceClassify>());

        if (null != list && list.size() >= 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("查询1级分类失败");
    }


    //添加1级分类
    @ApiOperation(value = "添加1级分类(不开放)")
    @RequestMapping(value = "/saveClassify", method = RequestMethod.PUT)
    public DdResult saveBrand(@RequestBody SysDeviceClassify sysDeviceClassify) {

        sysDeviceClassify.setStatus(0);
        boolean save = sysDeviceClassifyService.save(sysDeviceClassify);
        if (save) {
            return DdResult.ok("添加1级分类成功");
        }
        return DdResult.fail("添加1级分类失败");
    }


    //回显1级分类
    @ApiOperation(value = "回显1级分类查询(不开放)")
    @GetMapping("/getClassify/{classifyId}")
    public DdResult getBrand(@PathVariable(value = "classifyId", required = true) String classifyId) {

        SysDeviceClassify sysDeviceClassify = sysDeviceClassifyService.getById(classifyId);

        if (null != sysDeviceClassify) {
            return DdResult.ok(sysDeviceClassify);
        }

        return DdResult.fail("查询1级分类失败");
    }

    //更改1级分类
    @ApiOperation(value = "更改1级分类(不开放)")
    @PostMapping(value = "/updateClassify")
    public DdResult updateBrand(@RequestBody SysDeviceClassify sysDeviceClassify) {

        boolean update = sysDeviceClassifyService.updateById(sysDeviceClassify);
        if (update) {
            return DdResult.ok("更改1级分类成功");
        }
        return DdResult.fail("更改1级分类失败");
    }

    //删除1级分类
    @ApiOperation(value = "删除1级分类(不开放)")
    @GetMapping("/delClassify/{classifyId}")
    public DdResult delBrand(@PathVariable(value = "classifyId", required = true) String classifyId) {

        SysDeviceClassify deviceClassify = sysDeviceClassifyService.getById(classifyId);

        deviceClassify.setStatus(1);

        boolean update = sysDeviceClassifyService.updateById(deviceClassify);

        if (update) {
            return DdResult.ok("删除1级分类成功");
        }
        return DdResult.fail("删除1级分类失败");
    }

}

