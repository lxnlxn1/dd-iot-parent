package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdPojectService;
import cn.dreamdeck.iot.service.DdPojectTeamService;
import cn.dreamdeck.iot.service.DdProjectDocService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectDoc;
import cn.dreamdeck.model.iot.DdProjectTeam;
import cn.dreamdeck.model.iot.vo.DdProjectList;
import cn.dreamdeck.model.iot.vo.DdProjectModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("project")
@Api(value = "project", tags = "项目管理")
public class DdProjectController {

    private DdPojectService ddPojectService;

    private DdPojectTeamService ddPojectTeamService;

    private DdProjectDocService ddProjectDocService;

    /**
     * 获取项目下所有用户
     */
    @ApiOperation(value = "获取项目下所有用户", notes = "获取项目下所有用户")
    @GetMapping("/getProjectUserList")
    public DdResult getProjectUserList(Integer projectId) {
        if (projectId == null) {
            return DdResult.fail("项目id不能为空");
        }

        QueryWrapper<DdProjectTeam> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
//
//        return DdResult.ok(ddPojectTeamService.list(Wrapper<DdProjectTeam>(wrapper.eq("1","1")));
        return null;
    }

    /**
     * 获取所有项目
     */
    /**
     * 根据用户id查询所属项目
     *
     * @return
     */
    @ApiOperation(value = "根据用户id查询所属项目", notes = "根据用户id查询所属项目")
    @GetMapping("/allPorjectList")
    public DdResult allPorjectList() {
        return DdResult.ok(ddPojectService.list(new QueryWrapper<>()));
    }


    /**
     * 根据用户id查询所属项目
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询所属项目", notes = "根据用户id查询所属项目")
    @GetMapping("/getUserProjectList")
    public DdResult getUserProjectList(Integer userId) {
        if (userId == null) {
            return DdResult.fail("用户id不能为空");
        }
        List<DdProjectTeam> list = ddPojectTeamService.list(userId);

        List<DdProject> projectList = ddPojectService.list(new QueryWrapper<DdProject>());

        List<DdProjectModel> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < projectList.size(); j++) {
                if (list.get(i).getProjectId() == projectList.get(j).getProjetId()) {
                    DdProjectModel dd = new DdProjectModel();
                    dd.setTeamlogId(list.get(i).getTeamlogId());
                    dd.setProjectId(list.get(i).getProjectId());
                    dd.setCreateTime(list.get(i).getCreateTime());
                    dd.setUserId(list.get(i).getUserId());
                    dd.setProjectName(projectList.get(j).getProjectName());
                    dd.setProjectImg(projectList.get(j).getProjectImg());
                    list1.add(dd);
                }
            }

        }
        return DdResult.ok(list1);
    }

    /**
     * 根据文档类型查询list
     */
    @ApiOperation(value = "文档列表", notes = "文档列表")
    @GetMapping("/getProjectDocList")
    public DdResult getProjectDocList(Integer projectDocType, Integer projectId) {
        if (projectDocType == null) {
            return DdResult.fail("类型不能为空");
        }
        List<DdProjectDoc> list = ddProjectDocService.docList(projectDocType, projectId);
        return DdResult.ok(list);
    }


    /**
     * 项目查询
     */
    @ApiOperation(value = "项目查询", notes = "项目查询")
    @GetMapping("/projectInfo")
    public DdResult projectInfo(String projectId) {
        if (StringUtils.isBlank(projectId)) {
            return DdResult.fail("项目id不能为空");
        }
        DdProject dp = ddPojectService.getById(projectId);
        if (dp == null) {
            return DdResult.fail("未查询到该项目");
        }
        return DdResult.ok(dp);
    }

    /**
     * 项目添加
     */
    @ApiOperation(value = "项目添加", notes = "项目添加")
    @PostMapping("/projectSave")
    public DdResult save(@Valid @RequestBody DdProject ddProject) {
        ddProject.setStatus(0);
        ddProject.setCreateTime(DateUtil.getTime());
        boolean b = ddPojectService.save(ddProject);
        if (b = true) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("失败");

    }

    /**
     * 项目文档添加
     */
    @ApiOperation(value = "项目文档添加", notes = "项目文档添加")
    @PostMapping("/projectDocSave")
    public DdResult docSave(@Valid @RequestBody DdProjectDoc ddProjectDoc) {
        ddProjectDoc.setStatus(0);
        ddProjectDoc.setCreateTime(DateUtil.getTime());
        boolean b = ddProjectDocService.saveProjectDoc(ddProjectDoc);
        if (b = true) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("失败");

    }

    /**
     * 项目列表
     */
    @ApiOperation(value = "项目列表", notes = "项目列表")
    @PostMapping("/list")
    public DdResult list() {
//        List<DdProject> projectList = ddPojectService.list();
        return DdResult.ok();
//        return DdResult.ok(projectList);

    }

    /**
     * 项目添加所属项目用户
     */
    @PostMapping("/removeProjectTeam")
    public DdResult removeProjectTeam(@RequestBody DdProjectList ddProjectList) {

        QueryWrapper<DdProjectTeam> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",ddProjectList.getProjectId()).eq("user_id",Integer.valueOf(ddProjectList.getUserList()));
        ddPojectTeamService.remove(wrapper);


        return DdResult.ok("成功");

    }

    @PostMapping("/saveProjectTeam")
    public DdResult saveProjectTeam(@RequestBody DdProjectList ddProjectList) {

        DdProjectTeam dpt = new DdProjectTeam();
        dpt.setCreateTime(DateUtil.getTime());
        dpt.setStatus(0);
        dpt.setProjectId(ddProjectList.getProjectId());
        dpt.setUserId(Integer.valueOf(ddProjectList.getUserList()));
        ddPojectTeamService.save(dpt);


        return DdResult.ok("成功");

    }


    @PostMapping("/saveTeam")
    public void userDtoSave(@RequestParam(value = "projectList", required = false) List<Integer> projectList, @RequestParam(value = "userId", required = false) Integer userId) {
        System.out.println("------------------------------------" + userId);

        for (int i = 0; i < projectList.size(); i++) {
            DdProjectTeam ddProjectTeam = new DdProjectTeam();
            ddProjectTeam.setProjectId(projectList.get(i));
            ddProjectTeam.setStatus(1);
            ddProjectTeam.setUserId(userId);
            ddProjectTeam.setCreateTime(DateUtil.getTime());
            ddPojectTeamService.save(ddProjectTeam);
        }
    }


    /**
     * 上传文件
     * 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
     *
     * @param fileImg 资源
     * @return DdResult(bucketName, filename)
     */
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @PostMapping("/upload")
    public DdResult upload(@RequestParam("fileImg") MultipartFile fileImg) {

        return ddPojectService.uploadFile(fileImg);
    }


}
