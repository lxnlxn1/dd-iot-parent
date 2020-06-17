package cn.dreamdeck.iot.mapper;

import cn.dreamdeck.model.iot.DdDevice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 所有设备列表 Mapper 接口
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Mapper
public interface DdDeviceMapper extends BaseMapper<DdDevice> {


}
