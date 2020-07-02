package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.model.iot.SysDeviceModel;
import cn.dreamdeck.model.iot.vo.ModelVo;
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
    @GetMapping(value = "/getAllTypeByClassifyIdAndBrandId/{typeId}/{brandId}")
    public DdResult getAllTypeByClassifyIdAndBrandId(@PathVariable(value = "typeId", required = true) String typeId, @PathVariable(value = "brandId", required = true) String brandId) {


        List<SysDeviceModel> list = sysDeviceModelService.list(new QueryWrapper<SysDeviceModel>().eq("type_id", typeId).eq("brand_id", brandId).eq("status", 0));

        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("设备型号查询失败");
    }

    @ApiOperation(value = "唯一设备型号分类查询（筛选功能）")
    @GetMapping(value = "/getAllTypeByClassifyId/{typeId}/{screenIds}")
    public DdResult getAllType(@PathVariable(value = "typeId", required = true) String typeId, @PathVariable(value = "screenIds", required = true) String screenIds) {

        List<SysDeviceModel> list = sysDeviceModelService.list(new QueryWrapper<SysDeviceModel>().eq("type_id", typeId).like("model_intro", screenIds).eq("status", 0));
        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("设备型号查询失败");
    }

    @ApiOperation(value = "根据2级分类返回设备型号（无筛选条件）")
    @GetMapping(value = "/getAllTypeByClassifyId/{typeId}")
    public DdResult getAllTypeByClassifyId(@PathVariable(value = "typeId", required = true) String typeId) {

        List<SysDeviceModel> list = sysDeviceModelService.list(new QueryWrapper<SysDeviceModel>().eq("type_id", typeId).eq("status", 0));

        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("设备型号查询失败");
    }


    @ApiOperation(value = "根据model返回modelVo")
    @GetMapping(value = "/getModelVoByModelId/{modelId}")
    public DdResult getModelVoByModelId(@PathVariable(value = "modelId", required = true) String modelId) {
        ModelVo modelVo = sysDeviceModelService.getModelVoByModelId(modelId);
        return DdResult.ok(modelVo);
    }


}

