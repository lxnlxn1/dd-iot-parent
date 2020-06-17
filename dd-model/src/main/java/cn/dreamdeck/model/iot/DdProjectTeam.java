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
 * 
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdProjectTeam对象", description="")
public class DdProjectTeam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "team_log_id", type = IdType.AUTO)
    private Integer teamLogId;

    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    private String createTime;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    private Integer status;

    @ApiModelProperty(value = "用户id")
    private Integer roleId;




}
