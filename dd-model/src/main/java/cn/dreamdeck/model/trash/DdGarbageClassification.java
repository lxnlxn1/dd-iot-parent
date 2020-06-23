package cn.dreamdeck.model.trash;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 垃圾分类表
 * </p>
 *
 * @author lxn
 * @since 2020-06-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdGarbageClassification对象", description="垃圾分类表")
public class DdGarbageClassification implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.ID_WORKER_STR)
    private String logId;

    private String name;

    private Integer category;

    private String createTime;


}
