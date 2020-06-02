package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdDeviceMeterWaterMapper;
import cn.dreamdeck.iot.service.DdDeviceMeterWaterService;
import cn.dreamdeck.model.iot.DdDeviceMeterWater;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDeviceMeterWaterServiceImpl extends ServiceImpl<DdDeviceMeterWaterMapper, DdDeviceMeterWater> implements DdDeviceMeterWaterService {

    @Override
    public IPage list(Page page, DdDeviceMeterWater ddDeviceMeterWater) {
        return null;
    }

    @Override
    public Boolean saveMeterWater(DdDeviceMeterWater ddDeviceMeterWater) {
        this.baseMapper.insert(ddDeviceMeterWater);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateMeterWater(DdDeviceMeterWater ddDeviceMeterWater) {
        this.baseMapper.updateById(ddDeviceMeterWater);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceMeterWater ddDeviceMeterWater) {
        this.baseMapper.deleteById(ddDeviceMeterWater);
        return Boolean.TRUE;
    }
}
