package cn.dreamdeck.iot.mapper;


import cn.dreamdeck.model.iot.DdDeviceGetway;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface DdDeviceGetWayMapper extends BaseMapper<DdDeviceGetway> {

    /**
     * 添加网关
     *
     * @param ddDeviceGetway
     * @return
     */
    Boolean saveGetwayCom(DdDeviceGetway ddDeviceGetway);

    /**
     * 修改网关
     *
     * @param ddDeviceGetway
     * @return
     */
    Boolean updateGetwayCom(DdDeviceGetway ddDeviceGetway);
}
