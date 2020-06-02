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
@ApiModel(value = "所有设备表")
@TableName("dd_device")
@EqualsAndHashCode(callSuper = true)
public class DdDevice extends Model<DdDevice> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.INPUT)
    @ApiModelProperty(value = "deviceId")
    private String deviceId;

    @TableField(value = "`upside_id`")
    @ApiModelProperty(value = "上级设备id")
    private String upsideId;

    @TableField(value = "`project_id`")
    @ApiModelProperty(value = "项目id")
    private String projectId;

    @TableField(value = "`user_id`")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @TableField(value = "`brand_id`")
    @ApiModelProperty(value = "设备品牌id")
    private String brandId;

    @TableField(value = "`model_id`")
    @ApiModelProperty(value = "设备型号id")
    private String modelId;

    @TableField(value = "`connect_baud`")
    @ApiModelProperty(value = "波特率")
    private Integer connectBaud;

    @TableField(value = "`classify_id`")
    @ApiModelProperty(value = "设备分类id")
    private Integer classifyId;

    @TableField(value = "`type_id`")
    @ApiModelProperty(value = "类别id")
    private Integer typeId;

    @TableField(value = "`device_name`")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @TableField(value = "`device_ip`")
    @ApiModelProperty(value = "设备ip")
    private String deviceIp;

    @TableField(value = "`subset_id`")
    @ApiModelProperty(value = "设备号")
    private Integer subsetId;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(value = "`longitude`")
    @ApiModelProperty(value = "经度")
    private String longitude;

    @TableField(value = "`latitude`")
    @ApiModelProperty(value = "纬度")
    private String latitude;

    @TableField(value = "`last_time`")
    @ApiModelProperty(value = "上次通讯时间")
    private String lastTime;

    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private String createTime;


}
