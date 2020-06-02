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
@ApiModel(value = "项目管理")
@TableName("dd_project")
@EqualsAndHashCode(callSuper = true)
public class DdProject extends Model<DdProject> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    @ApiModelProperty(value = "项目id")
    private Integer projetId;

    @NotNull(message = "项目名称不能为空")
    @TableField(value = "`project_name`")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @TableField(value = "`project_desc`")
    @ApiModelProperty(value = "项目描述")
    private String projectDesc;

    @TableField(value = "`project_type`")
    @ApiModelProperty(value = "项目描述")
    private Integer projectType;

    @NotNull(message = "项目地址不能为空")
    @TableField(value = "`project_site`")
    @ApiModelProperty(value = "项目地址")
    private String projectSite;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;

    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField(value = "`project_img`")
    @ApiModelProperty(value = "图片")
    private String projectImg;

}
