package cn.dreamdeck.model.iot.DeviceConfig;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="DdDevice回传对象")
public class DeviceConfig implements Serializable {


    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备型号")
    private Integer modelId;

    @ApiModelProperty(value = "用户id")
    private Integer installUserId;

    @ApiModelProperty(value = "用户名称")
    private String installUserName;

    @ApiModelProperty(value = "上级设备ID")
    private Integer upsideId;

    @ApiModelProperty(value = "设备IP")
    private String deviceIp;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "设备唯一id（公司）")
    private Integer soleId;


    @ApiModelProperty(value = "设备配置")
    private Object Config;
}
