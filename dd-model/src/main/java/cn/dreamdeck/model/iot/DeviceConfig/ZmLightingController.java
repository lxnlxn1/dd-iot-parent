package cn.dreamdeck.model.iot.DeviceConfig;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="DdDevice对象", description="所有设备列表")
public class ZmLightingController implements Serializable {

    @ApiModelProperty(value = "设备编号")
    private Integer deviceIdByScene;



}
