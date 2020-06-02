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
@ApiModel(value = "项目文档")
@TableName("dd_project_doc")
@EqualsAndHashCode(callSuper = true)
public class DdProjectDoc extends Model<DdProjectDoc> {

    @TableId(value = "project_doc_id", type = IdType.AUTO)
    @ApiModelProperty(value = "projectDocId")
    private Integer projectDocId;

    @TableId(value = "project_id", type = IdType.AUTO)
    @ApiModelProperty(value = "projectId")
    private Integer projectId;

    @NotNull(message = "文档名称不能为空")
    @TableField(value = "`project_doc_name`")
    @ApiModelProperty(value = "文档名称")
    private String projectDocName;

    @TableField(value = "`project_doc_url`")
    @ApiModelProperty(value = "文档url")
    private String projectDocUrl;

    @TableField(value = "`project_doc_type`")
    @ApiModelProperty(value = "文档类型")
    private Integer projectDocType;

    @TableField(value = "`create_time`")
    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态")
    private Integer status;
}
