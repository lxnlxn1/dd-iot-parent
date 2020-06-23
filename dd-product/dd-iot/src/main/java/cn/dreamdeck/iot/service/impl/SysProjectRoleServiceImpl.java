package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.iot.mapper.SysProjectRoleMapper;
import cn.dreamdeck.iot.service.*;
import cn.dreamdeck.model.iot.*;
import cn.dreamdeck.service.constant.RedisConst;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */
@Service
public class SysProjectRoleServiceImpl extends ServiceImpl<SysProjectRoleMapper, SysProjectRole> implements SysProjectRoleService {

    @Autowired
    private DdProjectRoleService ddProjectRoleService;
    @Autowired
    private DdProjectRoleMenuService ddProjectRoleMenuService;
    @Autowired
    private DdProjectTeamService ddProjectTeamService;

    @Autowired
    private DdMenuService ddMenuService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${version}")
    private Integer version;

    @Override
    @Transactional
    public boolean saveRole(String projectId, String roleName, String menuIds) {

        //添加进权限列表
        int insert = baseMapper.insert(new SysProjectRole().setRoleName(roleName).setProjectId(Integer.valueOf(projectId)).setRoleDesc(roleName).setDelFlag("0").setCreateTime(DateUtil.getTime()).setUpdateTime(DateUtil.getTime()));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SysProjectRole sysProjectRole = baseMapper.selectOne(new QueryWrapper<SysProjectRole>().eq("role_name", roleName).eq("project_id", projectId));

        //添加进项目权限表
        ddProjectRoleService.save(new DdProjectRole().setProjectId(Integer.valueOf(projectId)).setSysRoleId(sysProjectRole.getRoleId()));

        //添加到项目权限菜单表
        String[] split = menuIds.split(",");
        for (String menuId : split) {
            boolean save = ddProjectRoleMenuService.save(new DdProjectRoleMenu().setProjectId(Integer.valueOf(projectId)).setRoleId(sysProjectRole.getRoleId()).setVersion(version).setMenuId(Integer.valueOf(menuId)));
            return save;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delRole(String projectId, String roleId) {

        //删除权限列表
        SysProjectRole sysProjectRole = baseMapper.selectById(roleId);
        sysProjectRole.setDelFlag("1");
        baseMapper.updateById(sysProjectRole);
        //删除项目权限表
        ddProjectRoleService.remove(new QueryWrapper<DdProjectRole>().eq("project_id", projectId).eq("sys_role_id", roleId));
        //删除项目权限菜单表
        ddProjectRoleMenuService.remove(new QueryWrapper<DdProjectRoleMenu>().eq("project_id", projectId).eq("role_id", roleId).eq("version", version));
        //用户数据
        List<DdProjectTeam> ddProjectTeamList = ddProjectTeamService.list(new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("role_id", roleId));

        for (DdProjectTeam ddProjectTeam : ddProjectTeamList) {
            ddProjectTeam.setStatus("1");
            ddProjectTeam.setUpdateTime(DateUtil.getTime());
            ddProjectTeamService.updateById(ddProjectTeam);
        }


        return true;
    }


    @Override
    public List<DdMenu> getAllRoleByUserId(String projectId, String userId, String version) {
        List<DdMenu> ddMenuList = null;
        if (redisTemplate.hasKey(RedisConst.PROJECT_MENU_KEY_PREFIX + projectId + userId)) {
            ddMenuList = (List<DdMenu>) redisTemplate.opsForValue().get(RedisConst.PROJECT_MENU_KEY_PREFIX + projectId + userId);
        } else {
            List<DdProjectTeam> ddProjectTeamList = ddProjectTeamService.list((new QueryWrapper<DdProjectTeam>().eq("project_id", projectId).eq("user_id", userId)));
            ArrayList<Integer> integers = new ArrayList<>();
            for (DdProjectTeam ddProjectTeam : ddProjectTeamList) {
                integers.add(ddProjectTeam.getRoleId());
            }
            //获取菜单IDs
            ArrayList<Integer> ddProjectRoleMenusIds = new ArrayList<>();
            // ArrayList<List<DdProjectRoleMenu>> arrayList = new ArrayList<>();
            for (Integer integer : integers) {
                List<DdProjectRoleMenu> list = ddProjectRoleMenuService.list(new QueryWrapper<DdProjectRoleMenu>().eq("project_id", projectId).eq("role_id", integer));
                //arrayList.add(list);
                list.stream().forEach(f -> {
                    ddProjectRoleMenusIds.add(f.getMenuId());
                });
            }
            //去重
            List<Integer> ids = new ArrayList(new HashSet(ddProjectRoleMenusIds));
            if (ids.size() > 0) {
                for (int i = 0; i < ids.size(); i++) {
                    ddMenuList.add(ddMenuService.getOne(new QueryWrapper<DdMenu>().eq("menu_id", i).eq("version", version)));
                }
            }
            redisTemplate.opsForValue().set(RedisConst.PROJECT_MENU_KEY_PREFIX + projectId + userId, ddMenuList);
        }

        return ddMenuList;
    }


}
