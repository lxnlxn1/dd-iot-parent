package cn.dreamdeck.model.iot.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Data
@ApiModel(value="ModelVo前端对象", description="")
public class ModelVo {

    @ApiModelProperty(value = "设备ID")
    private Integer modelId;

    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "类别ID")
    private Integer typeId;

    private Object deviceConfig;

    @ApiModelProperty(value = "型号名称")
    private String modelName;

    @ApiModelProperty(value = "简介")
    private String modelIntro;


    @ApiModelProperty(value = "监控指标ID")
    private String dataList;

    @ApiModelProperty(value = "监控指标列表")
    private String dataListName;

    @ApiModelProperty(value = "设备图片")
    private String modelImg;

    @ApiModelProperty(value = "设备电源")
    private String modelPower;

    @ApiModelProperty(value = "设备功耗")
    private String modelPowerWaste;

    @ApiModelProperty(value = "通讯接口")
    private String modelCommunication;

    @ApiModelProperty(value = "安装描述")
    private String modelProfile;

    @ApiModelProperty(value = "工作温度")
    private String workTemperature;

    @ApiModelProperty(value = "设备端口数量")
    private Integer modelCom;

    @ApiModelProperty(value = "设备文档")
    private String modelDocument;






}
