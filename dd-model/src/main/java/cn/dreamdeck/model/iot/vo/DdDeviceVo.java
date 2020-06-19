package cn.dreamdeck.model.iot.vo;


import cn.dreamdeck.model.iot.SysDeviceBrand;
import cn.dreamdeck.model.iot.SysDeviceModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 所有设备列表
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdDevice对象", description="所有设备列表")
public class DdDeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "分类ID")
    private Integer classifyId;

    @ApiModelProperty(value = "设备品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "设备品牌")
    private SysDeviceBrand sysDeviceBrand;



    @ApiModelProperty(value = "设备型号ID")
    private Integer modelId;

    @ApiModelProperty(value = "设备型号")
    private SysDeviceModel  sysDeviceModel;



    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "波特率")
    private Integer connectBaud;

    private String deviceIp;

    private Integer subsetId;

    @ApiModelProperty(value = "设备类型")
    private Integer deviceType;

    @ApiModelProperty(value = "设备状态")
    private Integer status;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "最后通迅时间")
    private String lastTime;

    @ApiModelProperty(value = "安装时间")
    private String createTime;

    @ApiModelProperty(value = "安装人员")
    private String installUserName;




}
