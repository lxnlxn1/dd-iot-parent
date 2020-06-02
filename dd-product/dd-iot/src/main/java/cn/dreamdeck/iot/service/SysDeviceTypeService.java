package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.SysDeviceType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeviceTypeService extends IService<SysDeviceType> {

    List<SysDeviceType> list(Integer classifyId);

    SysDeviceType selectTypeId(String typeId);
}
