package cn.dreamdeck.model.iot;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="DdProject对象", description="")
public class DdProject implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目类型 1本地部署 2 云服务")
    private Integer projectType;

    @ApiModelProperty(value = "项目描述")
    private String projectAlias;

    @ApiModelProperty(value = "状态 1 删除 0 未删除")
    private Integer status;

    @ApiModelProperty(value = "项目图片")
    private String projectImg;

    @ApiModelProperty(value = "项目地址")
    private String projectSite;

    private Integer userId;

    private String userName;

    private String createTime;

    private String updateTime;

    @ApiModelProperty(value = "oa同步Id")
    private Integer itemId;

    @ApiModelProperty(value = "项目开始时间")
    private String startDate;

    @ApiModelProperty(value = "项目结束时间")
    private String endDate;

    @ApiModelProperty(value = "是否删除")
    @TableField("delFlag")
    private Integer delFlag;


}
