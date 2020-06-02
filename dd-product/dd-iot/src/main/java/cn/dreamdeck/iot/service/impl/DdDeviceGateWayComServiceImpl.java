package cn.dreamdeck.iot.service.impl;



import cn.dreamdeck.iot.mapper.DdDeviceGatewayComMapper;
import cn.dreamdeck.iot.service.DdDeviceGatewayComService;
import cn.dreamdeck.model.iot.DdDeviceGatewayCom;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DdDeviceGateWayComServiceImpl extends ServiceImpl<DdDeviceGatewayComMapper, DdDeviceGatewayCom> implements DdDeviceGatewayComService {
    @Override
    public Boolean saveGetway(DdDeviceGatewayCom ddDeviceGatewayCom) {
        this.baseMapper.insert(ddDeviceGatewayCom);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateGetway(DdDeviceGatewayCom ddDeviceGatewayCom) {
        this.baseMapper.updateById(ddDeviceGatewayCom);
        return Boolean.TRUE;
    }
}
