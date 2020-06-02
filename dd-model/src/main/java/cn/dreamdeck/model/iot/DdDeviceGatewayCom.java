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

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "网关设备")
@TableName("dd_device_gateway_com")
@EqualsAndHashCode(callSuper = true)
public class DdDeviceGatewayCom extends Model<DdDeviceGatewayCom> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "geteway_com_id", type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "网关端口id")
    private String gatewayComId;


    @NotNull(message = "网关id不能为空")
    @TableField(value = "`dd_geteway_id`")
    @ApiModelProperty(value = "网关设备id")
    private String ddGeteWayId;

    @TableField(value = "`com_id`")
    @ApiModelProperty(value = "端口号")
    private String comId;


    @TableField(value = "`baud_rate`")
    @ApiModelProperty(value = "波特率")
    private Integer baudRate;

    @TableField(value = "`check_bit`")
    @ApiModelProperty(value = "校验位")
    private String checkBit;

    @TableField(value = "`data_bit`")
    @ApiModelProperty(value = "数据位")
    private Integer dataBit;

    @TableField(value = "`stop_bit`")
    @ApiModelProperty(value = "停止位")
    private Integer stopBit;

    @TableField(value = "`work_type`")
    @ApiModelProperty(value = "工作方式")
    private String workType;

    @TableField(value = "`target_ip`")
    @ApiModelProperty(value = "目标ip")
    private String targetIp;

    @TableField(value = "`remote_com`")
    @ApiModelProperty(value = "远程端口")
    private Integer remoteCom;

    @TableField(value = "`local_com`")
    @ApiModelProperty(value = "本地端口")
    private Integer localCom;

}
