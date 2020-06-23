package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "设备品牌")
@RestController
@RequestMapping("/iot/deviceBrand")
public class SysDeviceBrandController {


    @Autowired
    private SysDeviceBrandService sysDeviceBrandService;


    //品牌厂家全部显示（含分类查询）
    @ApiOperation(value = "品牌厂家全部显示（含分类查询）")
    @GetMapping(value = "/getAllBrand")
    public DdResult getAllBrand() {

        List<SysDeviceBrand> list = sysDeviceBrandService.list(new QueryWrapper<SysDeviceBrand>());

        if (null != list && list.size() >= 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("查询厂家失败");
    }


    //添加品牌
    @ApiOperation(value = "添加品牌厂家(不开放)")
    @RequestMapping(value = "/saveBrand", method = RequestMethod.PUT)
    public DdResult saveBrand(@RequestBody SysDeviceBrand sysDeviceBrand) {

        sysDeviceBrand.setStatus(0);
        sysDeviceBrand.setCreateTime(DateUtil.getDay());
        boolean save = sysDeviceBrandService.save(sysDeviceBrand);
        if (save) {
            return DdResult.ok("添加品牌厂家成功");
        }
        return DdResult.fail("添加品牌厂家失败");
    }


    //品牌回显
    @ApiOperation(value = "回显品牌厂家")
    @GetMapping("/getBrand/{brandId}")
    public DdResult getBrand(@PathVariable(value = "brandId", required = true) String brandId) {

        SysDeviceBrand deviceBrand = sysDeviceBrandService.getById(brandId);

        if (null != deviceBrand) {
            return DdResult.ok(deviceBrand);
        }

        return DdResult.fail("添加设备失败");
    }

    //更改品牌
    @ApiOperation(value = "更改品牌厂家(不开放)")
    @PostMapping(value = "/updateBrand")
    public DdResult updateBrand(@RequestBody SysDeviceBrand sysDeviceBrand) {

        boolean update = sysDeviceBrandService.updateById(sysDeviceBrand);
        if (update) {
            return DdResult.ok("更改品牌成功");
        }
        return DdResult.fail("更改品牌失败");
    }

    //删除品牌厂家
    @ApiOperation(value = "删除品牌厂家(不开放)")
    @GetMapping("/delBrand/{brandId}")
    public DdResult delBrand(@PathVariable(value = "brandId", required = true) String brandId) {


        SysDeviceBrand deviceBrand = sysDeviceBrandService.getById(brandId);

        deviceBrand.setStatus(1);

        boolean update = sysDeviceBrandService.updateById(deviceBrand);

        if (update) {
            return DdResult.ok("删除品牌失败");
        }

        return DdResult.fail("删除品牌失败");
    }

}

