package cn.dreamdeck.lighting.controller;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.lighting.service.DdLightModelService;
import cn.dreamdeck.model.light.DdLightModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 路灯表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-02
 */
@RestController
@RequestMapping("/light/model")
public class DdLightModelController {

    @Autowired
    private DdLightModelService ddLightModelService;

    //添加模式管理

    @PostMapping("/saveModel")
    public DdResult saveModel(@RequestBody DdLightModel ddLightModel) {


        if (ddLightModel.getTimeCity() == null) {
            return DdResult.ok("时段开关城市错误");
        }
        if (ddLightModel.getTimeSeasons() == null) {
            return DdResult.ok("时段开关季节错误");
        }

        if (ddLightModel.getLightCity() == null) {
            return DdResult.ok("光照开关城市错误");
        }
        if (ddLightModel.getLightSeasons() == null) {
            return DdResult.ok("光照开关季节错误");
        }
        if (ddLightModel.getCloseTime() == null) {
            ddLightModel.setCloseTime("06:00:00");
            return DdResult.ok("关闭时间为空默认为早上6：00");
        }
        if (ddLightModel.getCloseTime() == null) {
            ddLightModel.setCloseTime("06:00:00");
//            return DdResult.ok("关闭时间为空默认为早上6：00");
        }
        ddLightModel.setReaction(01);
        ddLightModel.setUnmannedLuminance(50);
        ddLightModel.setCloseTime(DateUtil.getDay());
        ddLightModel.setLastTime(DateUtil.getDay());
        return DdResult.ok(ddLightModelService.save(ddLightModel));
    }


    //数据回显

    @GetMapping("/getModel")
    public DdResult saveModel(String lightModelId) {

        DdLightModel ddLightModel = ddLightModelService.getOne(new QueryWrapper<DdLightModel>().eq("model_id", lightModelId));
        if (ddLightModel == null) {
            return DdResult.fail("该模式不存在");


        }
        return DdResult.ok(ddLightModel);
    }
    //更改模式管理

    @PostMapping("/updateModel")
    public DdResult updateModel(@RequestBody DdLightModel ddLightModel) {


        if (ddLightModel.getTimeCity() == null) {
            return DdResult.ok("时段开关城市错误");
        }
        if (ddLightModel.getTimeSeasons() == null) {
            return DdResult.ok("时段开关季节错误");
        }

        if (ddLightModel.getLightCity() == null) {
            return DdResult.ok("光照开关城市错误");
        }
        if (ddLightModel.getLightSeasons() == null) {
            return DdResult.ok("光照开关季节错误");
        }
        if (ddLightModel.getCloseTime() == null) {
            ddLightModel.setCloseTime("06:00:00");
            return DdResult.ok("关闭时间为空默认为早上6：00");
        }
        if (ddLightModel.getCloseTime() == null) {
            ddLightModel.setCloseTime("06:00:00");
//            return DdResult.ok("关闭时间为空默认为早上6：00");
        }
        ddLightModel.setReaction(01);
        ddLightModel.setUnmannedLuminance(50);
        ddLightModel.setCloseTime(DateUtil.getDay());
        ddLightModel.setLastTime(DateUtil.getDay());
        return DdResult.ok(ddLightModelService.updateById(ddLightModel));
    }

    //删除模式

    @GetMapping("/delModel")
    public DdResult delModel(String lightModelId) {

        DdLightModel ddLightModel = ddLightModelService.getOne(new QueryWrapper<DdLightModel>().eq("model_id", lightModelId));
        if (ddLightModel == null) {
            return DdResult.fail("该模式不存在");
        }
        boolean removeById = ddLightModelService.removeById(lightModelId);
        return DdResult.ok(removeById);
    }

    //查询路灯
    @ApiOperation(value = "分页路灯模式(带条件)")
    @PostMapping("/getlightModelPageVo")
    public DdResult getTeacherPageVo(String current, String limit, String keyword) {
        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 10L;
        }

        Page<DdLightModel> page = new Page<>(current1, limit1);

        QueryWrapper<DdLightModel> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like("model_name", keyword);
        }
        //1.5根据创建时间倒排序
        wrapper.orderByDesc("last_time");
        //2调方法获取数据
        ddLightModelService.page(page, wrapper);
//        //3 从page获取数据
//        long total = page.getTotal();
//        List<DdLight> records = page.getRecords();
        //4 返回数据
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("total",total);
//        data.put("items",records);
//        return R.ok().data(data);
        return DdResult.ok(page);
    }

    //执行模式

    @GetMapping("/executeModel")
    public DdResult executeModel(String lightModelId) {



        return DdResult.ok("执行成功");

    }

}

