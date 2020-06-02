package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdDeviceSensorSoliMapper;
import cn.dreamdeck.iot.service.DdDeviceSensorSoliService;
import cn.dreamdeck.model.iot.DdDeviceSensorSoli;
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
public class DdDeviceSensorSoliServiceImpl extends ServiceImpl<DdDeviceSensorSoliMapper, DdDeviceSensorSoli> implements DdDeviceSensorSoliService {
    @Override
    public IPage list(Page page, DdDeviceSensorSoli ddDeviceSensorSoli) {
        return this.baseMapper.selectPage(page, new QueryWrapper<DdDeviceSensorSoli>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveSoli(DdDeviceSensorSoli ddDeviceSensorSoli) {
        this.baseMapper.insert(ddDeviceSensorSoli);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateSoli(DdDeviceSensorSoli ddDeviceSensorSoli) {
        this.baseMapper.updateById(ddDeviceSensorSoli);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceSensorSoli ddDeviceSensorSoli) {
        this.baseMapper.deleteById(ddDeviceSensorSoli);
        return Boolean.TRUE;
    }
}
