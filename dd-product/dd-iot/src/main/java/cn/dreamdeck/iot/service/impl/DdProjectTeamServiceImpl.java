package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdProjectTeamMapper;
import cn.dreamdeck.iot.service.DdPojectTeamService;
import cn.dreamdeck.model.iot.DdProjectTeam;
import cn.dreamdeck.service.util.CommonConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdProjectTeamServiceImpl extends ServiceImpl<DdProjectTeamMapper, DdProjectTeam> implements DdPojectTeamService {

    @Override
    public List<DdProjectTeam> list(Integer userId) {



        return this.baseMapper.selectList(new QueryWrapper<DdProjectTeam>().eq("user_id",userId));
    }

    @Override
    public IPage getProjectPage(Page page, DdProjectTeam ddProjectTeam) {




        return this.baseMapper.selectPage(page,new QueryWrapper<DdProjectTeam>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveProject(DdProjectTeam ddProjectTeam) {
        return null;
    }

    @Override
    public Boolean editBrand(DdProjectTeam ddProjectTeam) {
        return null;
    }

    @Override
    public Boolean deleteBrand(DdProjectTeam ddProjectTeam) {
        return null;
    }
}
