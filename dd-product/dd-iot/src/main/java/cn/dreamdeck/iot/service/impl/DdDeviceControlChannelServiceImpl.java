package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdDeviceControlChannelMapper;
import cn.dreamdeck.iot.service.DdDeviceControlChannelService;
import cn.dreamdeck.model.iot.DdDeviceControlChannel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DdDeviceControlChannelServiceImpl extends ServiceImpl<DdDeviceControlChannelMapper, DdDeviceControlChannel> implements DdDeviceControlChannelService{


    @Override
    public IPage list(Page page, DdDeviceControlChannel ddDeviceControlChannel) {

        IPage iPage = baseMapper.selectPage(page, new QueryWrapper<DdDeviceControlChannel>().orderByDesc(ddDeviceControlChannel.getCreateTime()));
        return iPage;
    }

    @Override
    public Boolean saveChannel(DdDeviceControlChannel ddDeviceControlChannel) {
        this.baseMapper.insert(ddDeviceControlChannel);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateChannel(DdDeviceControlChannel ddDeviceControlChannel) {
        this.baseMapper.updateById(ddDeviceControlChannel);
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteById(DdDeviceControlChannel ddDeviceControlChannel) {
        this.baseMapper.deleteById(ddDeviceControlChannel);
        return Boolean.TRUE;
    }
}
