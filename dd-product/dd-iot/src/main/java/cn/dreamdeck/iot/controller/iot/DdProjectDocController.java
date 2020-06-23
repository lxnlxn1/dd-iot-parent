package cn.dreamdeck.iot.controller.iot;


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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
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
    @ApiOperation(value = "查询项目文档")
    @GetMapping("/getProjectDoc/{projectId}/{projectDocType}")
    public DdResult getProjectDocByProjecId(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocType", required = true) String projectDocType) {

        List<DdProjectDoc> ddProjectDocList = ddProjectDocService.list(new QueryWrapper<DdProjectDoc>().eq("project_id", projectId).eq("project_doc_type", projectDocType));

        if (null != ddProjectDocList) {
            return DdResult.ok(ddProjectDocList);
        }
        return DdResult.fail("没有文件");
    }


    //上传文件
    @ApiOperation(value = "上传文件")
    @PostMapping("/saveProjectDoc/{projectId}/{projectDocTypeId}")
    public DdResult saveProjectDoc(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocTypeId", required = true) String projectDocTypeId, MultipartFile file) {

        boolean isOk = ddProjectDocService.saveFile(projectId, projectDocTypeId, file);

        if (isOk) {
            return DdResult.ok("上传成功");
        }
        return DdResult.fail("服务器错误");
    }


    //获取文件流
    @ApiOperation(value = "获取文件流")
    @GetMapping("/getProjectDocByName/{fileName}")

    public DdResult getProjectDocByName(@PathVariable(value = "fileName", required = true) String fileName) {

        InputStream inputStream = ddProjectDocService.getProjectDocByName(fileName);

        if (null != inputStream) {
            return DdResult.ok(inputStream);
        }

        return DdResult.fail("获取文件流失败");
    }

    //直接下载文件
    @ApiOperation(value = "直接下载文件")
    @GetMapping("/uploadProjectDocByDocId/{projectDocId}")

    public DdResult uploadProjectDocByName(@PathVariable(value = "projectDocId", required = true) String projectDocId, HttpServletRequest request, HttpServletResponse response) {

        ddProjectDocService.uploadProjectDocByName(request,response,projectDocId);

        return DdResult.ok("下载成功");
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

