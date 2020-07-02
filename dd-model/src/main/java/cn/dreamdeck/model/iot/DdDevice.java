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
 * 所有设备列表
 * </p>
 *
 * @author lxn
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdDevice对象", description="所有设备列表")
public class DdDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId(value = "device_id", type = IdType.ID_WORKER_STR)
    private String deviceId;

    @ApiModelProperty(value = "上级设备ID")
    private String upsideId;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    private Integer modelId;

    private Integer brandId;

    @ApiModelProperty(value = "分类ID")
    private Integer TypeId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "波特率")
    private Integer connectBaud;

    private String deviceIp;

    private Integer subsetId;

    @ApiModelProperty(value = "设备类型")
    private Integer deviceClassify;

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

    @ApiModelProperty(value = "安装人员Id")
    private Integer installUserId;

    @ApiModelProperty(value = "安装人员用户名")
    private String installUserName;


    private Integer soleId;



}
