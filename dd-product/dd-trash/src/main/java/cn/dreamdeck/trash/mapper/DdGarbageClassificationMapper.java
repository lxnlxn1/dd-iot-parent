package cn.dreamdeck.trash.mapper;

import cn.dreamdeck.model.trash.DdGarbageClassification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 垃圾分类表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-05-18
 */

@Mapper
public interface DdGarbageClassificationMapper  extends BaseMapper<DdGarbageClassification> {

    DdGarbageClassification likeName(@Param("name")String name);
}
