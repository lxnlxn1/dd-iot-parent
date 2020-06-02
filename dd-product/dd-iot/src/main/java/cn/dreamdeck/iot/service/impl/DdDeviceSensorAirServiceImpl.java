package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdDeviceSensorAirMapper;
import cn.dreamdeck.iot.service.DdDeviceSensorAirService;
import cn.dreamdeck.model.iot.DdDeviceSensorAir;
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
public class DdDeviceSensorAirServiceImpl extends ServiceImpl<DdDeviceSensorAirMapper, DdDeviceSensorAir> implements DdDeviceSensorAirService {
    @Override
    public IPage list(Page page, DdDeviceSensorAir ddDeviceSensorAir) {
        return this.baseMapper.selectPage(page,new QueryWrapper<DdDeviceSensorAir>().eq("status", CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveAir(DdDeviceSensorAir ddDeviceSensorAir) {
        this.baseMapper.insert(ddDeviceSensorAir);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateAir(DdDeviceSensorAir ddDeviceSensorAir) {
        this.baseMapper.updateById(ddDeviceSensorAir);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceSensorAir ddDeviceSensorAir) {
        this.baseMapper.deleteById(ddDeviceSensorAir);
        return Boolean.TRUE;
    }
}
