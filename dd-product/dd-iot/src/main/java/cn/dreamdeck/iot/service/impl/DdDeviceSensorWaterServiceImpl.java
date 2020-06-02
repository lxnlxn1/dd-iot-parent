package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.DdDeviceSensorWaterMapper;
import cn.dreamdeck.iot.service.DdDeviceSensorWaterService;
import cn.dreamdeck.model.iot.DdDeviceSensorWater;
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
public class DdDeviceSensorWaterServiceImpl extends ServiceImpl<DdDeviceSensorWaterMapper, DdDeviceSensorWater> implements DdDeviceSensorWaterService {
    @Override
    public IPage list(Page page, DdDeviceSensorWater ddDeviceSensorWater) {




        return this.baseMapper.selectPage(page,  new QueryWrapper<DdDeviceSensorWater>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveWater(DdDeviceSensorWater ddDeviceSensorWater) {
        this.baseMapper.insert(ddDeviceSensorWater);

        return Boolean.TRUE;
    }

    @Override
    public Boolean updateWater(DdDeviceSensorWater ddDeviceSensorWater) {
        this.baseMapper.updateById(ddDeviceSensorWater);

        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceSensorWater ddDeviceSensorWater) {
        this.baseMapper.deleteById(ddDeviceSensorWater);
        return Boolean.TRUE;
    }
}
