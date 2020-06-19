package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.DdDeviceMapper;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 所有设备列表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Service
@Slf4j
public class DdDeviceServiceImpl extends ServiceImpl<DdDeviceMapper, DdDevice> implements DdDeviceService {

    @Autowired
    private SysDeviceBrandService sysDeviceBrandService;

    @Autowired
    private SysDeviceModelService sysDeviceModelService;


    @Override
    public IPage<DdDeviceVo> pageDeviceVo(IPage<DdDevice> projectIPage) {

        List<DdDevice> deviceList = projectIPage.getRecords();
        List<DdDeviceVo> ddDeviceVos = new ArrayList<>();
        for (DdDevice ddDevice : deviceList) {
            DdDeviceVo ddDeviceVo = new DdDeviceVo();
            BeanUtils.copyProperties(ddDevice, ddDeviceVo);
            SysDeviceBrand sysDeviceBrand = sysDeviceBrandService.getById(ddDevice.getBrandId());
            ddDeviceVo.setSysDeviceBrand(sysDeviceBrand);
            ddDeviceVo.setSysDeviceModel(sysDeviceModelService.getById(ddDevice.getModelId()));
            ddDeviceVos.add(ddDeviceVo);
        }

        //先这样,以后有好方法再处理
        IPage<DdDeviceVo> ddDeviceVoIPage = new IPage<DdDeviceVo>() {
            @Override
            public List<DdDeviceVo> getRecords() {
                return ddDeviceVos;
            }

            @Override
            public IPage<DdDeviceVo> setRecords(List<DdDeviceVo> records) {
                return null;
            }

            @Override
            public long getTotal() {
                return projectIPage.getTotal();
            }

            @Override
            public IPage<DdDeviceVo> setTotal(long total) {
                return null;
            }

            @Override
            public long getSize() {
                return projectIPage.getSize();
            }

            @Override
            public IPage<DdDeviceVo> setSize(long size) {
                return null;
            }

            @Override
            public long getCurrent() {
                return projectIPage.getCurrent();
            }

            @Override
            public IPage<DdDeviceVo> setCurrent(long current) {
                return null;
            }
        };
        return ddDeviceVoIPage;
    }

    @Override
    public DdDeviceVo getDeviceVo(String deviceId) {

        DdDevice ddDevice = baseMapper.selectById(deviceId);

        if (ddDevice!=null){
            DdDeviceVo ddDeviceVo = new DdDeviceVo();
            BeanUtils.copyProperties(ddDevice, ddDeviceVo);
            SysDeviceBrand sysDeviceBrand = sysDeviceBrandService.getById(ddDevice.getBrandId());
            ddDeviceVo.setSysDeviceBrand(sysDeviceBrand);
            ddDeviceVo.setSysDeviceModel(sysDeviceModelService.getById(ddDevice.getModelId()));
            return ddDeviceVo;
        }

        return null;


    }


}
