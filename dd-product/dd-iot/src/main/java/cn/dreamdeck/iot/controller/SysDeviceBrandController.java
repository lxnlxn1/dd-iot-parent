package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("brand")
@Api(value = "brand", tags = "品牌管理")
public class SysDeviceBrandController {

    private SysDeviceBrandService sysDeviceBrandService;


    /**
     * 分页列表
     *
     * @param page
     * @param sysDeviceBrand
     * @return
     */
    @ApiOperation(value = "分页列表", notes = "分页列表")
    @GetMapping("/page")
    public DdResult getPage(Page page, SysDeviceBrand sysDeviceBrand) {
        return DdResult.ok(sysDeviceBrandService.getBrandPage(page, sysDeviceBrand));
    }

    /**
     * 添加分组
     *
     * @param sysDeviceBrand
     * @return
     */
    @ApiOperation(value = "添加品牌", notes = "添加品牌")
    @PostMapping("/")
    public DdResult save(@Valid @RequestBody SysDeviceBrand sysDeviceBrand) {
        return DdResult.ok(sysDeviceBrandService.saveBrand(sysDeviceBrand));
    }


    /**
     * 修改分组
     *
     * @param sysDeviceBrand
     * @return
     */
    @ApiOperation(value = "修改品牌", notes = "修改品牌")
    @PutMapping
    public DdResult edit(@Valid @RequestBody SysDeviceBrand sysDeviceBrand) {
        return DdResult.ok(sysDeviceBrandService.editBrand(sysDeviceBrand));
    }

    /**
     * 删除分组
     *
     * @param groupId
     * @return
     */
    @ApiOperation(value = "删除品牌", notes = "删除品牌")
    @DeleteMapping("/{groupId}")
    public DdResult delete(@PathVariable Integer groupId) {
        if (groupId == null) {
            return DdResult.fail("id不能为空");
        }
        SysDeviceBrand sysDeviceBrand = sysDeviceBrandService.getById(groupId);
        if (sysDeviceBrand == null) {
            return DdResult.fail("查无数据");
        }
        return DdResult.ok(sysDeviceBrandService.deleteBrand(sysDeviceBrand));
    }

}

