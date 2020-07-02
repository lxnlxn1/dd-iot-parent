package cn.dreamdeck.iot.controller.iot;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.common.util.AuthContextHolder;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.DdProjectTeamService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.vo.DdProjectVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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
@Api(description = "项目数据")
@RestController
@RequestMapping("/iot/project")
public class DdProjectController {


    @Autowired
    private DdProjectService ddProjectService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DdProjectTeamService projectTeamService;

    @Autowired
    private AuthContextHolder authContextHolder;


    //查询项目
    @ApiOperation(value = "根据用户id，查询项目数据（分页，查询）")
    @PostMapping("/getProjectPageVo/{current}/{limit}")
    public DdResult getProjectPageVo(@PathVariable(value = "current") String current, @PathVariable(value = "limit") String limit, @RequestParam(value = "ddProjectVo", required = false) DdProjectVo ddProjectVo, HttpServletRequest request) {


        String userId = authContextHolder.getUserId(request);


        if (userId == null) {
            return DdResult.fail("登录信息失效，请从新登录");
        }
        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 5L;


        }

        Page<DdProject> page = new Page<>(current1, limit1);

        QueryWrapper<DdProject> wrapper = new QueryWrapper<>();
        if (ddProjectVo != null) {


            if (StringUtils.isEmpty(ddProjectVo.getProjectName())) {
                wrapper.like("project_name", ddProjectVo.getProjectName());
            }

            if (StringUtils.isEmpty(ddProjectVo.getProjectType())) {
                wrapper.like("project_type", ddProjectVo.getProjectType());
            }

            if (StringUtils.isEmpty(ddProjectVo.getStatus())) {
                wrapper.like("status", ddProjectVo.getStatus());
            }

            if (StringUtils.isEmpty(ddProjectVo.getProjectSite())) {
                wrapper.like("project_site", ddProjectVo.getProjectSite());
            }
        }
        List<Integer> projectByUserId = projectTeamService.getProjectByUserId(userId);

        Integer integer = projectByUserId.get(0);

        //是管理员权限
        if (integer == -1) {
            wrapper.orderByDesc("create_time");
            IPage<DdProject> ddProjectIPage = ddProjectService.page(page, wrapper);
            return DdResult.ok(page);
        }
        List<DdProject> ddProjects = (List<DdProject>) ddProjectService.listByIds(projectByUserId);
        wrapper.orderByDesc("create_time");
        IPage<DdProject> ddProjectIPage = ddProjectService.page(page, wrapper);
        ddProjectIPage.setRecords(ddProjects);
        ddProjectIPage.setTotal(ddProjects.size());
        return DdResult.ok(page);
    }

    //查询项目
    @ApiOperation(value = "根据项目Id获取项目属性")
    @GetMapping("/getProjectById/{projectId}")
    public DdResult getProjectById(@PathVariable(value = "projectId", required = true) String projectId) {
        DdProject ddProject = ddProjectService.getById(projectId);
        return DdResult.ok(ddProject);
    }

    //更改项目
    @ApiOperation(value = "更改项目描述与地址")
    @RequestMapping(value = "/updateProject", method = RequestMethod.PUT)
    public DdResult updateProject(@RequestBody DdProjectVo ddProjectVo) {

        DdProject ddProject = ddProjectService.getById(ddProjectVo.getProjectId());
        String projectSite = ddProjectVo.getProjectSite();
        String projectDesc = ddProjectVo.getProjectDesc();
        if (!StringUtils.isEmpty(projectSite)) {
            ddProject.setProjectSite(projectSite);
        }
        if (!StringUtils.isEmpty(projectDesc)) {
            ddProject.setProjectSite(projectDesc);
        }
        ddProject.setUpdateTime(DateUtil.getTime());
        boolean b = ddProjectService.updateById(ddProject);
        if (b) {
            return DdResult.ok("更新成功");
        }
        return DdResult.ok("更新失败");
    }
}

