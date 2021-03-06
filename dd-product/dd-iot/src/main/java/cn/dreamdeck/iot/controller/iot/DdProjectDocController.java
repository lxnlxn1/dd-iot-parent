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
    @GetMapping("/getProjectDoc/{projectId}/{projectDocTypeId}")
    public DdResult getProjectDocByProjecId(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocTypeId", required = true) String projectDocTypeId) {

        List<DdProjectDoc> ddProjectDocList = ddProjectDocService.list(new QueryWrapper<DdProjectDoc>().eq("project_id", projectId).eq("project_doc_type", projectDocTypeId));

        if (null != ddProjectDocList) {
            return DdResult.ok(ddProjectDocList);
        }
        return DdResult.fail("没有文件");
    }


    //上传文件
    @ApiOperation(value = "上传项目文件")
    @PostMapping("/saveProjectDoc/{projectId}/{projectDocTypeId}")
    public DdResult saveProjectDoc(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "projectDocTypeId", required = true) String projectDocTypeId, @RequestBody MultipartFile file) {


        return DdResult.ok(ddProjectDocService.saveFile(projectId, projectDocTypeId, file));
    }


    //上传文件
    @ApiOperation(value = "上传设备文档")
    @PostMapping("/saveProjectDocBydevice/{modelId}")
    public DdResult saveProjectDocBydevice(@PathVariable(value = "modelId", required = true) String modelId, MultipartFile file) {

        boolean isOk = ddProjectDocService.saveFileDevice(modelId, file);

        if (isOk) {
            return DdResult.ok("上传成功");
        }
        return DdResult.fail("服务器错误");
    }

    @ApiOperation(value = "添加bucket")
    @GetMapping("/saveBucket/{bucketName}")

    public DdResult saveBucket(@PathVariable(value = "bucketName", required = true) String bucketName) {
        boolean isOk = ddProjectDocService.saveBucket(bucketName);
        if (isOk) {
            return DdResult.ok("添加成功");
        }
        return DdResult.fail("服务器错误");
    }

    @ApiOperation(value = "删除文件")
    @GetMapping("/delProjectDoc/{projectDocId}")

    public DdResult delProjectDoc(@PathVariable(value = "projectDocId", required = true) String projectDocId) {
        boolean isOk = ddProjectDocService.delProjectDoc(projectDocId);
        if (isOk) {
            return DdResult.ok("删除成功");
        }
        return DdResult.fail("服务器错误");
    }


//    //获取文件流
//    @ApiOperation(value = "获取文件流")
//    @GetMapping("/getProjectDocByName/{fileName}")
//
//    public DdResult getProjectDocByName(@PathVariable(value = "fileName", required = true) String fileName) {
//
//        InputStream inputStream = ddProjectDocService.getProjectDocByName(fileName);
//
//        if (null != inputStream) {
//            return DdResult.ok(inputStream);
//        }
//
//        return DdResult.fail("获取文件流失败");
//    }

    @ApiOperation(value = "获取下载地址")
    @GetMapping("/uploadProjectDocByDocId/{projectDocId}")

    public DdResult uploadProjectDocByName(@PathVariable(value = "projectDocId", required = true) String projectDocId) {

        String fileUrl = ddProjectDocService.uploadProjectDocByName(projectDocId);

        if (null != fileUrl) {
            return DdResult.ok(fileUrl);
        }
        return DdResult.fail(fileUrl);
    }

//    @ApiOperation(value = "获取下载流")
//    @GetMapping("/uploadProjectDocStreamByDocId/{projectDocId}")
//
//    public DdResult uploadProjectDocStreamByDocId(@PathVariable(value = "projectDocId", required = true) String projectDocId,HttpServletRequest request , HttpServletResponse response) {
//        ddProjectDocService.uploadProjectDocStreamByDocId(request,response,projectDocId);
//
//        return DdResult.ok();
//    }


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

