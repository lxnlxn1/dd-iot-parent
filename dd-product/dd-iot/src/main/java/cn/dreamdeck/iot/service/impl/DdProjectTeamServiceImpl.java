package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.mapper.DdProjectTeamMapper;
import cn.dreamdeck.iot.service.DdProjectTeamService;
import cn.dreamdeck.model.iot.DdProjectTeam;
import cn.dreamdeck.model.user.SysRole;
import cn.dreamdeck.user.client.SysUserFeignService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Service
public class DdProjectTeamServiceImpl extends ServiceImpl<DdProjectTeamMapper, DdProjectTeam> implements DdProjectTeamService {

    @Autowired
    private SysUserFeignService sysUserFeignService;


    @Override
    public List<Integer> getProjectByUserId(String userId) {

        List integers = new ArrayList<Integer>();
        //是否为超级管理员

        // Boolean isAdmin = false;
        DdResult result = sysUserFeignService.query(userId);
        List<SysRole> list = (List<SysRole>) result.getData();


        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                SysRole sysRole = JSON.parseObject(JSON.toJSONString(list.get(i)), SysRole.class);
                if (!sysRole.getDsType().isEmpty()) {
                    if ("0".equals(sysRole.getDsType())) {
                        integers.add(-1);
                        return integers;
                    }
                }
            }
        }
        List<DdProjectTeam> teamList = baseMapper.selectList(new QueryWrapper<DdProjectTeam>().eq("user_id", userId).eq("status",1));
        teamList.stream().
                forEach(f ->
                {
                    integers.add(f.getProjectId());
                });
        return integers;
    }
}
