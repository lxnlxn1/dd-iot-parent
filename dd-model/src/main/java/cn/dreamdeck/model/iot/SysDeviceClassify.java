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
@ApiModel(value = "设备分类管理")
@TableName("sys_device_classify")
@EqualsAndHashCode(callSuper = true)
public class SysDeviceClassify extends Model<SysDeviceClassify> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "classify_id", type = IdType.AUTO)
    @ApiModelProperty(value = "分类id")
    private Integer classifyId;

    @TableField(value = "`classify_name`")
    @ApiModelProperty(value = "分类名称")
    private String brandName;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;


}
