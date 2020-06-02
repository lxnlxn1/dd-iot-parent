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
@ApiModel(value = "品牌管理")
@TableName("sys_device_brand")
@EqualsAndHashCode(callSuper = true)
public class SysDeviceBrand extends Model<SysDeviceBrand> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "brand_id", type = IdType.AUTO)
    @ApiModelProperty(value = "品牌id")
    private String brandId;

    @TableField(value = "`classify_id`")
    @ApiModelProperty(value = "分类id")
    private Integer classifyId;

    @TableField(value = "`type_id`")
    @ApiModelProperty(value = "类别id")
    private Integer typeId;

    @TableField(value = "`brand_name`")
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;


    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private String createTime;


}