package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 设备型号表
 * </p>
 *
 * @author lxn
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysDeviceModel对象", description="设备型号表")
public class SysDeviceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "model_id", type = IdType.ID_WORKER_STR)
    private Integer modelId;

    @ApiModelProperty(value = "品牌ID")
    private Integer brandId;

    @ApiModelProperty(value = "类别ID")
    private Integer typeId;

    @ApiModelProperty(value = "型号名称")
    private String modelName;

    @ApiModelProperty(value = "简介")
    private String modelIntro;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    private String dataList;

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
