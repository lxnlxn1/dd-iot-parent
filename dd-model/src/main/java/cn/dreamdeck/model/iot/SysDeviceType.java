package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备类别管理
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDeviceType对象", description="设备类别管理")
public class SysDeviceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类别ID")
    @TableId(value = "type_id", type = IdType.ID_WORKER_STR)
    private Integer typeId;

    @ApiModelProperty(value = "分类ID")
    private Integer classifyId;

    @ApiModelProperty(value = "类别名称")
    private String typeName;

    @ApiModelProperty(value = "类别状态")
    private Integer status;

    @ApiModelProperty(value = "类别url")
    private String url;


}
