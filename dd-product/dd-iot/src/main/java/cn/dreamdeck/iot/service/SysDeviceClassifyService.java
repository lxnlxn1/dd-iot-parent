package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.SysDeviceClassify;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeviceClassifyService extends IService<SysDeviceClassify> {

    /**
     * list
     */
    List<SysDeviceClassify> getClassifyList();


}
