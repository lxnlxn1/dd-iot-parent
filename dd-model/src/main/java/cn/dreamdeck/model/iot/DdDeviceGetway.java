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
@ApiModel(value = "网关设备")
@TableName("dd_device_gateway")
@EqualsAndHashCode(callSuper = true)
public class DdDeviceGetway extends Model<DdDeviceGetway> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "device_id", type = IdType.INPUT)
    @ApiModelProperty(value = "deviceId")
    private String deviceId;

    @TableField(value = "`upside_id`")
    @ApiModelProperty(value = "上级设备id")
    private String upsideId;

    @NotNull(message = "项目id不能为空")
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

    //0 静态ip 1 动态ip
    @NotNull(message = "设备网络类型不能为空")
    @TableField(value = "`device_network_type`")
    @ApiModelProperty(value = "设备网络类型")
    private Integer deviceNetworkType;

    @TableField(value = "`device_ip`")
    @ApiModelProperty(value = "设备ip")
    private String deviceIp;

    @NotNull(message = "设备网络mac地址不能为空")
    @TableField(value = "`device_mac`")
    @ApiModelProperty(value = "设备mac地址")
    private String deviceMac;


    @TableField(value = "`device_subnet_mask`")
    @ApiModelProperty(value = "子网掩码")
    private String deviceSubnetMask;

    @TableField(value = "`device_getway`")
    @ApiModelProperty(value = "网关")
    private String deviceGetway;


    @TableField(value = "`device_com_list`")
    @ApiModelProperty(value = "设备端口列表")
    private String deviceComList;


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
