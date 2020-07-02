package cn.dreamdeck.iot.controller.device;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDictData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-22
 */
@Api(description = "2级分类字典表")
@RestController
@RequestMapping("/iot/data")
public class SysDictDataController {

    @Autowired
    private SysDictDataService sysDictDataService;


    //设备1级分类查询
    @ApiOperation(value = "所有设备2级分类字典表")
    @GetMapping(value = "/getAllData")
    public DdResult getAllData() {

        List<SysDictData> list = sysDictDataService.list(new QueryWrapper<SysDictData>().eq("status", 0));

        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("查询2级字典表失败");
    }

    //设备1级分类查询
    @ApiOperation(value = "设备2级分类字典表")
    @GetMapping(value = "/getAllDataByTypeId/{typeId}")
    public DdResult getAllTypeByClassifyId(@PathVariable(value = "typeId", required = true) String typeId) {

        List<SysDictData> list = sysDictDataService.list(new QueryWrapper<SysDictData>().eq("type_id", typeId).eq("status", 0));

        if (null != list && list.size() > 0) {
            return DdResult.ok(list);
        }
        return DdResult.fail("查询2级字典表失败");
    }

    //设备1级分类查询
    @ApiOperation(value = "添加设备2级分类字典表")
    @RequestMapping(value = "/saveData",method = RequestMethod.PUT)
    public DdResult saveData(@RequestBody SysDictData sysDictData) {

        sysDictData.setStatus("0");

        boolean save = sysDictDataService.save(sysDictData);
        if (save){
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("添加失败");
    }


    //设备1级分类查询
    @ApiOperation(value = "回显设备2级分类字典表")
    @GetMapping(value = "/getDataById/{dataId}")
    public DdResult getDataById(@PathVariable(value = "dataId", required = true) String dataId) {

        SysDictData sysDictData = sysDictDataService.getById(dataId);

        if (null!=sysDictData){
            return DdResult.ok(sysDictData);
        }
        return DdResult.fail("添加失败");
    }

    //设备1级分类查询
    @ApiOperation(value = "更改设备2级分类字典表")
    @PostMapping(value = "/updateData")
    public DdResult updateData(@RequestBody SysDictData sysDictData) {

        boolean update = sysDictDataService.updateById(sysDictData);
        if (update){
            return DdResult.ok("更新成功");
        }
        return DdResult.fail("添加失败");
    }

    //设备1级分类查询
    @ApiOperation(value = "删除设备2级分类字典表")
    @GetMapping(value = "/delDataById/{dataId}")
    public DdResult delDataById(@PathVariable(value = "dataId", required = true) String dataId) {
        SysDictData sysDictData = sysDictDataService.getById(dataId);
        sysDictData.setStatus("1");
        boolean update = sysDictDataService.updateById(sysDictData);
        if (update){
            return DdResult.ok("删除成功");
        }
        return DdResult.fail("删除失败");
    }






    }

