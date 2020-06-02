package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.SysDeviceBrandMapper;
import cn.dreamdeck.iot.service.SysDeviceBrandService;
import cn.dreamdeck.model.iot.SysDeviceBrand;
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
public class SysDeviceBrandServiceImpl extends ServiceImpl<SysDeviceBrandMapper, SysDeviceBrand> implements SysDeviceBrandService {
    @Override
    public IPage getBrandPage(Page page, SysDeviceBrand sysDeviceBrand) {
        return this.baseMapper.selectPage(page,new QueryWrapper<SysDeviceBrand>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public SysDeviceBrand selectByBrandId(String brandId) {
        return this.baseMapper.selectById(brandId);
    }

    @Override
    public SysDeviceBrand selectById(String brandId) {

        return this.baseMapper.selectOne(new QueryWrapper<SysDeviceBrand>().eq("brand_name",brandId));
    }

    @Override
    public Boolean saveBrand(SysDeviceBrand sysDeviceBrand) {
        this.baseMapper.insert(sysDeviceBrand);
        return Boolean.TRUE;

    }


    @Override
    public Boolean editBrand(SysDeviceBrand sysDeviceBrand) {
        this.baseMapper.updateById(sysDeviceBrand);
        return Boolean.TRUE;

    }

    @Override
    public Boolean deleteBrand(SysDeviceBrand sysDeviceBrand) {
        sysDeviceBrand.setStatus(Integer.valueOf(CommonConstants.STATUS_DEL));
        this.updateById(sysDeviceBrand);
        return Boolean.TRUE;
    }
}
