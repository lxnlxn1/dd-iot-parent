package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.model.iot.SysDeviceModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 设备型号表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */
@Api(description = "设备型号（3级）")
@RestController
@RequestMapping("/iot/deviceModel")
public class SysDeviceModelController {

    @Autowired
    private SysDeviceModelService sysDeviceModelService;


    //设备1级分类查询
    @ApiOperation(value = "唯一设备型号分类查询（需要品牌Id）")
    @GetMapping(value = "/getAllTypeByClassifyId/{typeId}/{brandId}")
    public DdResult getAllTypeByClassifyId(@PathVariable(value = "typeId", required = true) String typeId, @PathVariable(value = "brandId", required = true) String brandId) {


        List<SysDeviceModel> list = sysDeviceModelService.list(new QueryWrapper<SysDeviceModel>().eq("type_id", typeId).eq("brand_id", brandId));

        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("设备型号查询失败");
    }

    @ApiOperation(value = "唯一设备型号分类查询（筛选功能）")
    @GetMapping(value = "/getAllTypeByClassifyId/{typeId}/{screen}")
    public DdResult getAllType(@PathVariable(value = "typeId", required = true) String typeId,@PathVariable(value = "screen", required = true) String screen) {
        List<SysDeviceModel> list = sysDeviceModelService.list(new QueryWrapper<SysDeviceModel>().eq("type_id", typeId).like("model_intro",screen));
        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("设备型号查询失败");
    }




}

