package cn.dreamdeck.model.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "设备详情")
public class DdDeviceInfoVo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备品牌")
    private String brandName;
    @ApiModelProperty(value = "设备型号")
    private String modelName;
    @ApiModelProperty(value = "供电电源")
    private String modelPower;
    @ApiModelProperty(value = "通讯接口")
    private String modelCommunication;
    @ApiModelProperty(value = "平均功耗")
    private String modelPowerWaste;
    @ApiModelProperty(value = "监测指标")
    private String dataList;
    @ApiModelProperty(value = "设备编号")
    private String deviceId;
    @ApiModelProperty(value = "经度")
    private String longitude;
    @ApiModelProperty(value = "纬度")
    private String latitude;
    @ApiModelProperty(value = "设备id")
    private String subsetId;
    @ApiModelProperty(value = "波特率")
    private String connectBaud;
    @ApiModelProperty(value = "用户id")
    private String userName;
    @ApiModelProperty(value = "上级设备")
    private String upsideId;
}
