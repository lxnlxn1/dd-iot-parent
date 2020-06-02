package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "设备类别")
@TableName("sys_device_type")
@EqualsAndHashCode(callSuper = true)
public class SysDeviceType extends Model<SysDeviceType> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id", type = IdType.AUTO)
    @ApiModelProperty(value = "设备类别id")
    private Integer typeId;

    @TableField(value = "`classify_id`")
    @ApiModelProperty(value = "分类id")
    private Integer classifyId;

    @TableField(value = "`type_name`")
    @ApiModelProperty(value = "设备类型名称")
    private String typeName;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(value = "`url`")
    @ApiModelProperty(value = "跳转url")
    private String url;

}
