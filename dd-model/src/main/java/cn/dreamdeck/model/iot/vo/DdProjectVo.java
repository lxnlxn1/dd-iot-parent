package cn.dreamdeck.model.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value="DdProject前端对象", description="")
public class DdProjectVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目类型 1本地部署 2 云服务")
    private Integer projectType;

    @ApiModelProperty(value = "状态 1 删除 0 未删除")
    private Integer status;

    @ApiModelProperty(value = "项目后地址")
    private String projectSite;

    @ApiModelProperty(value = "项目描述")
    private String projectDesc;




}
