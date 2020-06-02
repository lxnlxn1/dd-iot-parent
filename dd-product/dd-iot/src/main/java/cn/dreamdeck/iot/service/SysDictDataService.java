package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.SysDictData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDictDataService extends IService<SysDictData> {

    List<SysDictData> list(Integer sysDictData);

    SysDictData selectById(String dictCode);
}
