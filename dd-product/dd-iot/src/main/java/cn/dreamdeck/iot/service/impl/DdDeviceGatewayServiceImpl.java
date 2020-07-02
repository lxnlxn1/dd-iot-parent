package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.iot.mapper.DdDeviceGatewayMapper;
import cn.dreamdeck.iot.service.DdDeviceGatewayService;
import cn.dreamdeck.iot.service.DdDeviceService;
import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.device.gateway.DdDeviceGateway;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-07-01
 */
@Service
public class DdDeviceGatewayServiceImpl extends ServiceImpl<DdDeviceGatewayMapper, DdDeviceGateway> implements DdDeviceGatewayService {

    @Autowired
    private DdDeviceService ddDeviceService;

    @Override
    public IPage<DdDeviceGateway> getTeacherPageVo(String current, String limit, String projectId) {


        Long current1 = null;
        Long limit1 = null;

        try {
            current1 = Long.valueOf(current);
            limit1 = Long.valueOf(limit);
        } catch (NumberFormatException e) {

            current1 = 0L;
            limit1 = 5L;
        }
        Page<DdDeviceGateway> ddDeviceGatewayPage = new Page<>(current1, limit1);

        IPage<DdDeviceGateway> ddDeviceGatewayIPage = baseMapper.selectPage(ddDeviceGatewayPage, new QueryWrapper<DdDeviceGateway>().eq("project_id", projectId).orderByDesc("create_time"));
        return ddDeviceGatewayIPage;
    }

    @Transactional
    @Override
    public boolean updateGateway(DdDeviceGateway ddDeviceGateway) {
        ddDeviceGateway.setLastTime(DateUtil.getTime());
        baseMapper.updateById(ddDeviceGateway);
        DdDevice ddDevice = ddDeviceService.getById(ddDeviceGateway.getDeviceId());
        BeanUtils.copyProperties(ddDeviceGateway, ddDevice);
        ddDeviceService.updateById(ddDevice);
        return true;
    }
    @Transactional
    @Override
    public boolean delGateway(String gatewayId) {

        DdDeviceGateway ddDeviceGateway = baseMapper.selectById(gatewayId);
        ddDeviceService.removeById(ddDeviceGateway.getDeviceId());
        baseMapper.deleteById(gatewayId);
        return true;
    }
}
