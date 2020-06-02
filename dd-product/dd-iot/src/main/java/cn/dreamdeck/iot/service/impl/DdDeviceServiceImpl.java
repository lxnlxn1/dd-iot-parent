package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdDeviceMapper;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.vo.DdDeviceInfoVo;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DdDeviceServiceImpl extends ServiceImpl<DdDeviceMapper, DdDevice> implements DdDeviceService {

    @Override
    public Boolean saveDevice(DdDevice ddDevice) {
        this.baseMapper.insert(ddDevice);

        return Boolean.TRUE;
    }

    @Override
    public Boolean updateDevice(DdDevice ddDevice) {
        this.baseMapper.updateById(ddDevice);

        return Boolean.TRUE;
    }

    @Override
    public Boolean delectDevice(DdDevice ddDevice) {
        this.baseMapper.deleteById(ddDevice);

        return Boolean.TRUE;
    }

    @Override
    public IPage<DdDeviceVo> getDeviceList(Page page, Integer projectId, String classifyId) {

        return this.baseMapper.getDeviceList(page, projectId, classifyId);
    }

    @Override
    public IPage<DdDeviceVo> getTotalDeviceList(Page page) {
        return this.baseMapper.getTotalDeviceList(page);
    }

    @Override
    public DdDeviceInfoVo deviceInfo(String deviceId) {
        return this.baseMapper.deviceInfo(deviceId);
    }


    //根据Ip与型号是否有设备
    @Override
    public DdDevice selectIpAndModelId(String ip, String modelId) {

        DdDevice ddDevice = baseMapper.selectOne(new QueryWrapper<DdDevice>().eq("device_ip", ip).eq("model_id", modelId));
        return ddDevice;
    }
}
