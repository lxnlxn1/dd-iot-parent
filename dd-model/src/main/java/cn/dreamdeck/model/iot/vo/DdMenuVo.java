package cn.dreamdeck.model.iot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "DdMenu对象", description = "菜单权限表")
public class DdMenuVo implements Serializable {

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    @ApiModelProperty(value = "菜单ID")
    private String label;

    @ApiModelProperty(value = "菜单ID")
    private List<DdMenuVo> children;



//
//    @ApiModelProperty(value = "菜单ID")
//    private Integer menuId;
//
//    @ApiModelProperty(value = "名称")
//    private String name;
//
//    @ApiModelProperty(value = "父菜单ID")
//    private Integer parentId;
//
//    @ApiModelProperty(value = "前端渲染class")
//    private String frontClass;
//
//    @ApiModelProperty(value = "系统版本")
//    private Integer version;
//
//    @ApiModelProperty(value = "图标")
//    private String icon;
//
//    @ApiModelProperty(value = "排序值")
//    private Integer sort;
//
//    @ApiModelProperty(value = "存活")
//    private String keepAlive;
//
//    @ApiModelProperty(value = "地址")
//    private String path;
//
//    @ApiModelProperty(value = "创建时间")
//    private Date createTime;
//
//    @ApiModelProperty(value = "更新时间")
//    private Date updateTime;
//
//    @ApiModelProperty(value = "是否删除")
//    private String delFlag;
//
//    private List<DdMenuVo> ddMenuVoList;


}
