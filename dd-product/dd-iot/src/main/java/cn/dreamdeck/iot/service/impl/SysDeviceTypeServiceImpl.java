package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.SysDeviceTypeMapper;
import cn.dreamdeck.iot.service.SysDeviceTypeService;
import cn.dreamdeck.model.iot.SysDeviceType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysDeviceTypeServiceImpl extends ServiceImpl<SysDeviceTypeMapper, SysDeviceType> implements SysDeviceTypeService {
    @Override
    public List<SysDeviceType> list(Integer classifyId) {



        return this.baseMapper.selectList(new QueryWrapper<SysDeviceType>().eq("classify_id", classifyId));
    }

    @Override
    public SysDeviceType selectTypeId(String typeId) {
        return this.baseMapper.selectById(typeId);
    }


}
