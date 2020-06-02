package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.SysDeviceModelMapper;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.model.iot.SysDeviceModel;
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
public class SysDeviceModelServiceImpl extends ServiceImpl<SysDeviceModelMapper, SysDeviceModel> implements SysDeviceModelService {

    @Override
    public IPage getModelPage(Page page, SysDeviceModel sysDeviceModel) {
        if (sysDeviceModel.getModelName() != null) {


            return this.baseMapper.selectPage(page,  new QueryWrapper<SysDeviceModel>().eq("status",CommonConstants.STATUS_NORMAL).like("model_name",sysDeviceModel.getModelName()).orderByDesc("create_time"));
        }


        return this.baseMapper.selectPage(page,new QueryWrapper<SysDeviceModel>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public IPage getTypePage(Page page, SysDeviceModel sysDeviceModel) {
        if (sysDeviceModel.getTypeId() != null) {


            return this.baseMapper.selectPage(page, new QueryWrapper<SysDeviceModel>().eq("status",CommonConstants.STATUS_NORMAL).like("type_id",sysDeviceModel.getTypeId()).orderByDesc("create_time"));
        }


        return this.baseMapper.selectPage(page, new QueryWrapper<SysDeviceModel>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public SysDeviceModel getModelId(String modelId) {
        return this.baseMapper.selectById(modelId);
    }


    @Override
    public Boolean saveBrand(SysDeviceModel sysDeviceModel) {
        return null;
    }

    @Override
    public Boolean editBrand(SysDeviceModel sysDeviceModel) {
        return null;
    }

    @Override
    public Boolean deleteBrand(SysDeviceModel sysDeviceModel) {
        return null;
    }
}
