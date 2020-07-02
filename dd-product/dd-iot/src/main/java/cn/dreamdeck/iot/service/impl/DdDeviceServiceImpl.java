package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.iot.mapper.DdDeviceMapper;
import cn.dreamdeck.iot.service.*;
import cn.dreamdeck.model.iot.*;
import cn.dreamdeck.model.iot.DeviceConfig.DeviceConfig;
import cn.dreamdeck.model.iot.DeviceConfig.TjhyWaterMeter;
import cn.dreamdeck.model.iot.DeviceConfig.WhjxSoilSensor;
import cn.dreamdeck.model.iot.DeviceConfig.ZmLightingController;
import cn.dreamdeck.model.iot.device.gateway.DdDeviceGateway;
import cn.dreamdeck.model.iot.device.innovate.DdTrashConfig;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private SysDeviceTypeService sysDeviceTypeService;

    @Autowired
    private SysDeviceClassifyService sysDeviceClassifyService;

    @Autowired
    private DdTrashConfigService ddTrashConfigService;

    @Autowired
    private DdDeviceGatewayService ddDeviceGatewayService;


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

        if (ddDevice != null) {
            DdDeviceVo ddDeviceVo = new DdDeviceVo();
            BeanUtils.copyProperties(ddDevice, ddDeviceVo);
            SysDeviceBrand sysDeviceBrand = sysDeviceBrandService.getById(ddDevice.getBrandId());
            ddDeviceVo.setSysDeviceBrand(sysDeviceBrand);
            ddDeviceVo.setSysDeviceModel(sysDeviceModelService.getById(ddDevice.getModelId()));
            return ddDeviceVo;
        }
        return null;
    }

    @Transactional
    @Override
    public boolean saveDeviceVo(DeviceConfig deviceConfig) {

        DdDevice ddDevice = new DdDevice();
        BeanUtils.copyProperties(deviceConfig, ddDevice);
        SysDeviceModel deviceModel = sysDeviceModelService.getById(deviceConfig.getModelId());
        ddDevice.setBrandId(deviceModel.getBrandId());
        ddDevice.setTypeId(deviceModel.getTypeId());
        ddDevice.setStatus(0);
        ddDevice.setLastTime(DateUtil.getTime());
        ddDevice.setCreateTime(DateUtil.getTime());
        SysDeviceType Type = sysDeviceTypeService.getById(deviceModel.getTypeId());
        SysDeviceClassify classify = sysDeviceClassifyService.getById(Type.getClassifyId());
        ddDevice.setDeviceClassify(classify.getClassifyId());
        ddDevice.setSoleId(deviceConfig.getSoleId());


        //后期其他设备在添加更换方法，或换策略模式
        switch (classify.getClassifyId()) {
            case 1: //网关设备
                DdDeviceGateway ddDeviceGateway = (DdDeviceGateway) deviceConfig.getConfig();
                ddDeviceGateway.setProjectId(deviceConfig.getProjectId());
                ddDeviceGateway.setBrandId(deviceModel.getBrandId());
                ddDeviceGateway.setModelId(deviceConfig.getModelId());
                ddDeviceGateway.setDeviceName(deviceConfig.getDeviceName());
                ddDeviceGateway.setLastTime(DateUtil.getTime());
                ddDeviceGateway.setCreateTime(DateUtil.getTime());
                ddDeviceGatewayService.save(ddDeviceGateway);
                break;
            case 2: //环境感知设备
//                switch (deviceConfig.getModelId()) {
//                    case 3:
//                        UsrGateWay usrGateWay1 = (UsrGateWay) deviceConfig.getConfig();
//                        return ddTrashConfigService.save(ddTrashConfig);
//                }


                WhjxSoilSensor whjxSoilSensor = (WhjxSoilSensor) deviceConfig.getConfig();
                break;
            case 3: //可执行设备

                TjhyWaterMeter tjhyWaterMeter = (TjhyWaterMeter) deviceConfig.getConfig();

                break;
            case 4://可联网设备


                ZmLightingController zmLightingController = (ZmLightingController) deviceConfig.getConfig();
                break;
            case 5://自研互动设备

                switch (deviceConfig.getModelId()) {
                    case 13:
                        DdTrashConfig ddTrashConfig = (DdTrashConfig) deviceConfig.getConfig();
                        ddTrashConfigService.save(ddTrashConfig);
                        break;
                }


                break;
            case 6://可家居设备
                break;

        }


        baseMapper.insert(ddDevice);
        return false;
    }


}
