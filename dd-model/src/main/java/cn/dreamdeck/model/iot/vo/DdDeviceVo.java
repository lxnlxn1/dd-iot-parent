package cn.dreamdeck.model.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "现场版设备列表")
public class DdDeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备id")
    private String deviceId;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "设备品牌")
    private String brandName;
    @ApiModelProperty(value = "设备型号")
    private String ModelName;
    @ApiModelProperty(value = "状态")
    private Integer status;
    @ApiModelProperty(value = "创建时间")
    private String createTime;
    @ApiModelProperty(value = "安装人员")
    private String userName;
    @ApiModelProperty(value = "url")
    private String url;
    @ApiModelProperty(value = "modelId")
    private String modelId;
}
