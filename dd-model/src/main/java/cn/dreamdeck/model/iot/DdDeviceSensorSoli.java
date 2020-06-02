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

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "土壤设备")
@TableName("dd_device_sensor_soli")
@EqualsAndHashCode(callSuper = true)
public class DdDeviceSensorSoli extends Model<DdDeviceSensorSoli> {
    private static final long serialVersionUID = 1L;
    @NotNull(message = "设备id不能为空")
    @TableId(value = "device_id", type = IdType.INPUT)
    @ApiModelProperty(value = "deviceId")
    private String deviceId;

    @TableField(value = "`upside_id`")
    @ApiModelProperty(value = "上级设备id")
    private String upsideId;

    @NotNull(message = "网关id不能为空")
    @TableField(value = "`gateway_id`")
    @ApiModelProperty(value = "网关id")
    private String gatewayId;

    @NotNull(message = "用户id不能为空")
    @TableField(value = "`project_id`")
    @ApiModelProperty(value = "项目id")
    private String projectId;

    @NotNull(message = "用户id不能为空")
    @TableField(value = "`user_id`")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @NotNull(message = "设备品牌id不能为空")
    @TableField(value = "`brand_id`")
    @ApiModelProperty(value = "设备品牌id")
    private String brandId;

    @NotNull(message = "设备型号id不能为空")
    @TableField(value = "`model_id`")
    @ApiModelProperty(value = "设备型号id")
    private String modelId;

    @NotNull(message = "设备名称不能为空")
    @TableField(value = "`device_name`")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @NotNull(message = "设备地址不能为空")
    @TableField(value = "`subset_id`")
    @ApiModelProperty(value = "设备地址")
    private Integer subsetId;

    @NotNull(message = "连接端口不能为空")
    @TableField(value = "`connect_port`")
    @ApiModelProperty(value = "连接端口")
    private Integer connectPort;

    @NotNull(message = "连接波特率不能为空")
    @TableField(value = "`connect_baud`")
    @ApiModelProperty(value = "连接波特率")
    private Integer connectBaud;

    @TableField(value = "`status`")

    @ApiModelProperty(value = "状态")
    private Integer status;

    @NotNull(message = "经度不能为空")
    @TableField(value = "`longitude`")
    @ApiModelProperty(value = "经度")
    private String longitude;

    @NotNull(message = "纬度不能为空")
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
