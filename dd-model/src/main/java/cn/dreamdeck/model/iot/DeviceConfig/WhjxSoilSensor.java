package cn.dreamdeck.model.iot.DeviceConfig;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="威海精讯土壤传感器", description="所有设备列表")
public class WhjxSoilSensor implements Serializable {


    @ApiModelProperty(value = "设备编号")
    private Integer deviceIdByScene;


    @ApiModelProperty(value = "modBusId")
    private Integer modBusId;

    @ApiModelProperty(value = "波特率")
    private Integer connectBaud;

    @ApiModelProperty(value = "上级设备名称")
    private Integer upsideId;



}
