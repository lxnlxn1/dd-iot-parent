package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.service.DdProjectRoleService;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdProjectTeamService;
import cn.dreamdeck.iot.service.SysProjectRoleService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectRole;
import cn.dreamdeck.model.iot.DdProjectTeam;
import cn.dreamdeck.model.iot.SysProjectRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */

@Api(description = "项目角色表")
@RestController
@RequestMapping("/iot/projectRole")
public class SysProjectRoleController {

    @Autowired
    private DdProjectRoleService ddProjectRoleService;

    @Autowired
    private SysProjectRoleService sysProjectRoleService;

    @Autowired
    private DdProjectService projectService;

    @Autowired
    private DdProjectTeamService ddProjectTeamService;

    @Autowired
    private AuthContextHolder authContextHolder;

    //分类列表
    @ApiOperation(value = "根据项目id返回权限列表")
    @GetMapping("/getAllRoleByProjectId/{projectId}")
    public DdResult getAllRoleByProjectId(@PathVariable(value = "projectId", required = true) String projectId) {

        List<DdProjectRole> projectRoleList = ddProjectRoleService.list(new QueryWrapper<DdProjectRole>().eq("project_id", projectId));

        ArrayList<Integer> roleList = new ArrayList<>();
        projectRoleList.stream().forEach(f -> {
            roleList.add(f.getProjectRoleId());
        });
        List<SysProjectRole> sysProjectRoles = (List) sysProjectRoleService.listByIds(roleList);


        System.out.println(sysProjectRoles);
        return DdResult.ok(sysProjectRoles);
    }

    //添加
    @ApiOperation(value = "添加权限列表")
    @GetMapping("/saveRoleProject/{projectId}/{roleId}")
    public DdResult saveRoleProject(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId, HttpServletRequest request) {

        String userId = authContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {

            return DdResult.fail("登录异常");
        }

        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userId) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }

        boolean save = ddProjectRoleService.save(new DdProjectRole().setProjectId(Integer.valueOf(projectId)).setProjectRoleId(Integer.valueOf(roleId)));

        if (save) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("添加失败");
    }

    //删除
    @ApiOperation(value = "删除权限列表")
    @RequestMapping(value = "/delRoleProject/{projectId}/{roleId}",method = RequestMethod.DELETE)
    public DdResult delRoleProject(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId, HttpServletRequest request) {

        String userId = authContextHolder.getUserId(request);

        if (StringUtils.isEmpty(userId)) {
            return DdResult.fail("登录异常");
        }
        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userId) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }
        //删除人员--------------goto
        List<DdProjectTeam> ddProjectTeamList = ddProjectTeamService.list(new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("role_id", roleId));
        for (DdProjectTeam ddProjectTeam : ddProjectTeamList) {
            DdProjectTeam ddProjectTeam1 = new DdProjectTeam();
            ddProjectTeam.setStatus(1);
            ddProjectTeamService.updateById(ddProjectTeam1);
        }
        boolean b = ddProjectRoleService.removeById(Integer.valueOf(projectId));
        if (b) {
            return DdResult.ok("删除成功");
        }
        return DdResult.ok("删除失败");
    }


}

