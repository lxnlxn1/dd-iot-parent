package cn.dreamdeck.model.light;

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
 * 路灯表
 * </p>
 *
 * @author lxn
 * @since 2020-06-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdLightModel对象", description="路灯表")
public class DdLightModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备ID")
    @TableId(value = "model_id", type = IdType.AUTO)
    private Integer modelId;

    @ApiModelProperty(value = "城市")
    private String timeCity;

    @ApiModelProperty(value = "季节")
    private String timeSeasons;

    @ApiModelProperty(value = "路数编号")
    private String lightCity;

    private String lightSeasons;

    private String closeTime;

    private String startTime;

    private Integer startLuminance;

    private String endTime;

    private Integer endLuminance;

    private Integer reaction;

    private Integer unmannedLuminance;

    private Integer reactionLuminance;

    private String createTime;

    private String lastTime;

    private String modelName;


}
