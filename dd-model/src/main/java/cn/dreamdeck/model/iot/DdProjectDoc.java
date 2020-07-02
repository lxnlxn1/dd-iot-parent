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
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdProjectDoc对象", description="")
public class DdProjectDoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目文档Id")
    @TableId(value = "project_doc_id", type = IdType.AUTO)
    private Integer projectDocId;

    @ApiModelProperty(value = "项目Id")
    private Integer projectId;

    @ApiModelProperty(value = "项目文档名称")
    private String projectDocName;

    @ApiModelProperty(value = "项目文档类型")
    private Integer projectDocType;

    @ApiModelProperty(value = "设备Id")
    private String modelId;

    @ApiModelProperty(value = "创建日期")
    private String createTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "bucket_id")
    private String bucketName;


}
