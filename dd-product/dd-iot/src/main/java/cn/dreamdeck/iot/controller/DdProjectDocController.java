package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdDocTypeService;
import cn.dreamdeck.iot.service.DdProjectDocService;
import cn.dreamdeck.model.iot.DdDocType;
import cn.dreamdeck.model.iot.DdProjectDoc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-18
 */

@Api(description = "项目文档管理")
@RestController
@RequestMapping("/iot/projectDoc")
public class DdProjectDocController {

    @Autowired
    private DdDocTypeService ddDocTypeService;

    @Autowired
    private DdProjectDocService ddProjectDocService;

    //查询项目文档类别
    @ApiOperation(value = "查询项目文档类别")
    @GetMapping("/getProjectDoc")
    public DdResult getProjectDoc() {

        List<DdDocType> list = ddDocTypeService.list(null);
        if (null != list) {
            return DdResult.ok(list);
        }
        return DdResult.fail("服务器错误");
    }

    //查询项目文档
    @ApiOperation(value = "查询项目文档类别")
    @GetMapping("/getProjectDoc/{projectId}/{projectDocType}")
    public DdResult getProjectDocByProjecId(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocType", required = true) String projectDocType) {

        List<DdProjectDoc> ddProjectDocList = ddProjectDocService.list(new QueryWrapper<DdProjectDoc>().eq("project_id", projectId).eq("project_doc_type", projectDocType));

        if (null != ddProjectDocList) {
            return DdResult.ok(ddProjectDocList);
        }
        return DdResult.fail("服务器错误");
    }



    //上传文件
    @ApiOperation(value = "上传文件")
    @PostMapping("/getProjectDoc/{projectId}/{projectDocType}")
    public DdResult getProjectDocByProjecId(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocType", required = true) String projectDocType,@RequestParam("fileName") MultipartFile file){



        return DdResult.fail("服务器错误");
    }




//    //添加项目文档类别
//    @ApiOperation(value = "添加项目文档类别")
//    @GetMapping("/saveProjectDoc")
//    public DdResult saveProjectDoc() {
//
//        return DdResult.ok();
//    }
//
//    //添加项目文档类别
//    @ApiOperation(value = "添加项目文档类别")
//    @GetMapping("/updateProjectDoc")
//    public DdResult updateProjectDoc() {
//
//        return DdResult.ok();
//    }
//
//    //删除项目文档类别
//    @ApiOperation(value = "删除项目文档类别")
//    @GetMapping("/delProjectDoc")
//    public DdResult delProjectDoc() {
//
//        return DdResult.ok();
//    }


}

