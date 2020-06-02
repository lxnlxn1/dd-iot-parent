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
@ApiModel(value = "项目组管理")
@TableName("dd_project_team")
@EqualsAndHashCode(callSuper = true)
public class DdProjectTeam extends Model<DdProjectTeam> {


    private static final long serialVersionUID = 1L;

    @TableId(value = "team_log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "logid")
    private Integer teamlogId;

    @TableField(value = "`project_id`")
    @ApiModelProperty(value = "项目id")
    private Integer projectId;

    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "类别id")
    private String createTime;

    @TableField(value = "`user_id`")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;
}
