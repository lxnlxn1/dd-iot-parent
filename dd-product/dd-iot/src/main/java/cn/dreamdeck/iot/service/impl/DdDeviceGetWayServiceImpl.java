package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.DdDeviceGetWayMapper;
import cn.dreamdeck.iot.service.DdDeviceGetWayService;
import cn.dreamdeck.model.iot.DdDeviceGetway;
import cn.dreamdeck.service.util.CommonConstants;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDeviceGetWayServiceImpl extends ServiceImpl<DdDeviceGetWayMapper, DdDeviceGetway> implements DdDeviceGetWayService {


    @Override
    public IPage list(Page page, DdDeviceGetway ddDeviceGetway) {

        return this.baseMapper.selectPage(page,new QueryWrapper<DdDeviceGetway>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveGetway(DdDeviceGetway ddDeviceGetway) {
        this.baseMapper.insert(ddDeviceGetway);
        return Boolean.TRUE;

    }

    @Override
    public Boolean updateGetway(DdDeviceGetway ddDeviceGetway) {
        this.baseMapper.updateById(ddDeviceGetway);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceGetway ddDeviceGetway) {
        this.baseMapper.deleteById(ddDeviceGetway);
        return Boolean.TRUE;

    }

}
