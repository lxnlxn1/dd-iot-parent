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

@Data
@ApiModel(value = "设备类型管理")
@TableName("sys_device_model")
@EqualsAndHashCode(callSuper = true)
public class SysDeviceModel extends Model<SysDeviceModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "model_id", type = IdType.AUTO)
    @ApiModelProperty(value = "分类id")
    private String modelId;

    @TableField(value = "`brand_id`")
    @ApiModelProperty(value = "分类名称")
    private String brandId;

    @TableField(value = "`type_id`")
    @ApiModelProperty(value = "状态")
    private String typeId;

    @TableField(value = "`model_name`")
    @ApiModelProperty(value = "分类名称")
    private String modelName;

    @TableField(value = "`model_intro`")
    @ApiModelProperty(value = "简介")
    private String modelIntro;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField(value = "`data_list`")
    @ApiModelProperty(value = "监测类型")
    private String DateList;

    @TableField(value = "`model_img`")
    @ApiModelProperty(value = "设备图片")
    private String modelmg;

    @TableField(value = "`model_power`")
    @ApiModelProperty(value = "供电电源")
    private String modelPower;

    @TableField(value = "`model_power_waste`")
    @ApiModelProperty(value = "功耗")
    private String modelPowerWaste;

    @TableField(value = "`model_communication`")
    @ApiModelProperty(value = "通讯接口")
    private String modelCommunication;

    @TableField(value = "`model_ profile`")
    @ApiModelProperty(value = "安装描述")
    private String modelProfile;

    @TableField(value = "`work_temperature`")
    @ApiModelProperty(value = "工作温度")
    private String workTemperature;

    @TableField(value = "`model_com`")
    @ApiModelProperty(value = "设备端口数量")
    private Integer modelCom;

    @TableField(value = "`model_document`")
    @ApiModelProperty(value = "设备文档图片")
    private String modelDocument;


}
