package cn.dreamdeck.trash.service;

import cn.dreamdeck.model.trash.DdGarbageClassification;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DdGarbageClassificationService  extends IService<DdGarbageClassification> {

    DdGarbageClassification likeName(String name);
}
