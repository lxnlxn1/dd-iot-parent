package cn.dreamdeck.model.light;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 路灯表
 * </p>
 *
 * @author lxn
 * @since 2020-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "DdLight对象", description = "路灯表")
public class DdLight implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "light_id", type = IdType.AUTO)
    private Integer lightId;

    @ApiModelProperty(value = "路灯类型")
    private Integer lightType;

    @ApiModelProperty(value = "设备ID")
    private String deviceId;

    @ApiModelProperty(value = "路数编号")
    private String lightWay;

    private String longitude;

    private String latitude;

    private String lightValue;

    private String createTime;

    private Integer status;

    private String projectId;

    private String maxLeave;

    private String minLeave;

    private Date startTime;

    private Date endTime;

    private Integer mode;

    private String lastTime;

    private Integer initialElectricity;

    private Integer lastElectricity;

    private Integer zoneId;


}
