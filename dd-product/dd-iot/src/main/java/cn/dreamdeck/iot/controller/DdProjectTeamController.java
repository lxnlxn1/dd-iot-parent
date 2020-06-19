package cn.dreamdeck.iot.controller;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdProjectTeamService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectTeam;
import cn.dreamdeck.model.user.SysUser;
import cn.dreamdeck.user.client.SysUserFeignService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */

@Api(description = "项目成员管理")
@RestController
@RequestMapping("/iot/projectTeam")
public class DdProjectTeamController {


    @Autowired
    private DdProjectTeamService ddProjectTeamService;

    @Autowired
    private SysUserFeignService sysUserFeignService;

    @Autowired
    private DdProjectService projectService;

    @Autowired
    private AuthContextHolder authContextHolder;


    //查询项目人员
    @ApiOperation(value = "查询项目人员")
    @GetMapping("/getProjectUserById/{projectId}")
    public DdResult getProjectById(@PathVariable(value = "projectId", required = true) String projectId) {

        List<DdProjectTeam> list = ddProjectTeamService.list(new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("status", 0));

        StringBuffer userIds = new StringBuffer();
        for (DdProjectTeam ddProjectTeam : list) {
            System.out.println(ddProjectTeam.getUserId());

              userIds.append(ddProjectTeam.getUserId() + ",");
        }
        List<SysUser> sysUserList = sysUserFeignService.getUserByIds(userIds.toString());
        return DdResult.ok(sysUserList);
    }

    //查询项目各工种人员
    @ApiOperation(value = "查询项目各类别人员")
    @GetMapping("/getProjectTeamById/{projectId}/{roleId}")
    public DdResult getrojectTeamById(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId) {

        List<DdProjectTeam> projectTeams = ddProjectTeamService.list(new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("status", 0).eq("role_id", roleId));
        StringBuffer userIds = new StringBuffer();;
        for (DdProjectTeam ddProjectTeam : projectTeams) {
            userIds.append(ddProjectTeam.getUserId() + ",");
        }
        List<SysUser> sysUserList = sysUserFeignService.getUserByIds(userIds.toString());
        return DdResult.ok(sysUserList);
    }

    //添加项目各工种人员
    @ApiOperation(value = "添加项目各类别人员")
    @RequestMapping(value = "/saveProjectTeamById/{projectId}/{roleId}/{userId}",method = RequestMethod.PUT)
    public DdResult saveProjectTeamById(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId, @PathVariable(value = "userId", required = true) String userId, HttpServletRequest request) {


        String userIdLogin = authContextHolder.getUserId(request);

        if (StringUtils.isEmpty(userIdLogin)) {
            return DdResult.fail("登录异常");
        }

        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userIdLogin) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }

        boolean save = ddProjectTeamService.save(new DdProjectTeam().setProjectId(Integer.valueOf(projectId)).setRoleId(Integer.valueOf(roleId)).setUserId(Integer.valueOf(userId)).setStatus(0).setCreateTime(DateUtil.getTime()));
        if (save) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("添加失败");
    }

    //删除项目各工种人员
    @ApiOperation(value = "删除项目各类别人员")
    @RequestMapping(value = "/delProjectTeamById/{projectId}/{roleId}/{userId}",method = RequestMethod.DELETE)
    public DdResult delProjectTeamById(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId, @PathVariable(value = "userId", required = true) String userId, HttpServletRequest request) {


        String userIdLogin = authContextHolder.getUserId(request);


        if (StringUtils.isEmpty(userIdLogin)) {
            return DdResult.fail("登录异常");
        }

        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userIdLogin) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }

        DdProjectTeam ddProjectTeam = ddProjectTeamService.getOne(new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("user_id", userId).eq("role_id", roleId));

        ddProjectTeam.setStatus(1);

        boolean b = ddProjectTeamService.updateById(ddProjectTeam);

        if (b) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("添加失败");
    }



}

