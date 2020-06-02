package cn.dreamdeck.iot.mapper;


import cn.dreamdeck.model.iot.DdDevice;
import cn.dreamdeck.model.iot.vo.DdDeviceInfoVo;
import cn.dreamdeck.model.iot.vo.DdDeviceVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DdDeviceMapper extends BaseMapper<DdDevice> {

    IPage<DdDeviceVo> getDeviceList(Page page, @Param("projectId") Integer projectId, @Param("classifyId") String classifyId);

    IPage<DdDeviceVo> getTotalDeviceList(Page page);

    DdDeviceInfoVo deviceInfo(@Param("deviceId") String deviceId);
}
