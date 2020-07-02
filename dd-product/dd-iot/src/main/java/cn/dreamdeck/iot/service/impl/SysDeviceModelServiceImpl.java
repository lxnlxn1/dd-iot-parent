package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.SysDeviceModelMapper;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.SysDeviceModel;
import cn.dreamdeck.model.iot.SysDictData;
import cn.dreamdeck.model.iot.vo.ModelVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备型号表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */
@Service
public class SysDeviceModelServiceImpl extends ServiceImpl<SysDeviceModelMapper, SysDeviceModel> implements SysDeviceModelService {

    @Autowired
    private SysDeviceBrandService sysDeviceBrandService;

    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public ModelVo getModelVoByModelId(String modelId) {
        ModelVo modelVo = new ModelVo();

        SysDeviceModel sysDeviceModel = baseMapper.selectById(modelId);
        BeanUtils.copyProperties(sysDeviceModel, modelVo);
        if (null != sysDeviceModel) {
            SysDeviceBrand deviceBrand = sysDeviceBrandService.getById(sysDeviceModel.getBrandId());
            modelVo.setBrandName(deviceBrand.getBrandName());
            String dataList = sysDeviceModel.getDataList();
            String[] split = dataList.split(",");

            String data = null;
            if (null != split && split.length > 0) {
                for (String ids : split) {
                    SysDictData sysDictData = sysDictDataService.getById(ids);
                    data += sysDictData.getDictLabel() + "、";
                }
                data = data.substring(0, data.length() - 1);
            }
            modelVo.setDataListName(data);
        }
        return modelVo;
    }
}
