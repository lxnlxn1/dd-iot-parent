package cn.dreamdeck.iot.controller.iot;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.service.*;
import cn.dreamdeck.model.iot.DdMenu;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectRole;
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


    @Autowired
    private DdMenuService ddMenuService;

    //分类列表
    @ApiOperation(value = "根据项目id返回权限列表")
    @GetMapping("/getAllRoleByProjectId/{projectId}")
    public DdResult getAllRoleByProjectId(@PathVariable(value = "projectId", required = true) String projectId) {

        List<DdProjectRole> projectRoleList = ddProjectRoleService.list(new QueryWrapper<DdProjectRole>().eq("project_id", projectId));
        ArrayList<Integer> roleList = new ArrayList<>();
        projectRoleList.stream().forEach(f -> {
            roleList.add(f.getSysRoleId());
        });
        List<SysProjectRole> sysProjectRoles = (List) sysProjectRoleService.listByIds(roleList);
        // System.out.println(sysProjectRoles);
        return DdResult.ok(sysProjectRoles);
    }

    @ApiOperation(value = "返回全部菜单列表")
    @GetMapping("/getAllMenu")
    public DdResult getAllMenu() {

        List<DdMenu> ddMenuList = ddMenuService.list(new QueryWrapper<DdMenu>());

        if (ddMenuList == null && ddMenuList.size() <= 0) {
            return DdResult.fail("服务器错误");
        }
        return DdResult.fail(ddMenuList);
    }

    //添加
    @ApiOperation(value = "添加权限列表")
    @RequestMapping(value = "/saveRoleProject/{projectId}/{roleName}/{menuIds}", method = RequestMethod.PUT)
    public DdResult saveRoleProject(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleName", required = true) String roleName, @PathVariable(value = "menuIds", required = true) String menuIds, HttpServletRequest request) {

        String userId = authContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return DdResult.fail("登录异常");
        }
        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userId) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }

        boolean save = sysProjectRoleService.saveRole(projectId, roleName, menuIds);

        if (save) {
            return DdResult.ok("添加成功");
        }
        return DdResult.ok("添加失败");
    }

    //删除
    @ApiOperation(value = "删除权限列表")
    @RequestMapping(value = "/delRoleProject/{projectId}/{roleId}", method = RequestMethod.DELETE)
    public DdResult delRoleProject(@PathVariable(value = "projectId", required = true) String projectId, @PathVariable(value = "roleId", required = true) String roleId, HttpServletRequest request) {

        String userId = authContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return DdResult.fail("登录异常");
        }
        DdProject ddProject = projectService.getById(projectId);
        if (Integer.valueOf(userId) != ddProject.getUserId()) {
            return DdResult.fail("没有权限");
        }

        boolean del = sysProjectRoleService.delRole(projectId, roleId);
        if (del) {
            return DdResult.ok("删除成功");
        }
        return DdResult.ok("删除失败");
    }

    //分类列表
    @ApiOperation(value = "根据项目id返回用户权限")
    @GetMapping("/getAllRoleByUserId/{projectId}")
    public DdResult getAllRoleByUserId(@PathVariable(value = "projectId", required = true) String projectId, HttpServletRequest request) {
        String userId = authContextHolder.getUserId(request);
        if (StringUtils.isEmpty(userId)) {
            return DdResult.fail("登录异常");
        }
        String version = authContextHolder.getUserVersion(request);
        List<DdMenu> ddMenuList = sysProjectRoleService.getAllRoleByUserId(projectId, userId,version);
        return DdResult.ok(ddMenuList);
    }
}

