package cn.dreamdeck.trash.service.impl;


import cn.dreamdeck.model.trash.DdGarbageClassification;
import cn.dreamdeck.trash.mapper.DdGarbageClassificationMapper;
import cn.dreamdeck.trash.service.DdGarbageClassificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 垃圾分类表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-05-18
 */
@Slf4j
@Service
@AllArgsConstructor
public class DdGarbageClassificationServiceImpl extends ServiceImpl<DdGarbageClassificationMapper, DdGarbageClassification> implements DdGarbageClassificationService {


    @Override
    public DdGarbageClassification likeName(String name) {


        return this.baseMapper.likeName(name);
    }


}
