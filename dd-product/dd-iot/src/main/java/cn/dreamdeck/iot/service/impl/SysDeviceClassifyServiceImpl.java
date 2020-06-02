package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.SysDeviceClassifyMapper;
import cn.dreamdeck.iot.service.SysDeviceClassifyService;
import cn.dreamdeck.model.iot.SysDeviceClassify;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysDeviceClassifyServiceImpl extends ServiceImpl<SysDeviceClassifyMapper, SysDeviceClassify> implements SysDeviceClassifyService {

    @Override
    public List<SysDeviceClassify> getClassifyList() {
        return this.baseMapper.selectList(null);
    }
}
