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
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DdProjectRoleMenu对象", description="")
public class DdProjectRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "项目Id")
    private Integer projectId;

    @ApiModelProperty(value = "权限ID")
    private Integer roleId;

    @ApiModelProperty(value = "菜单列表")
    private Integer menuId;

    @ApiModelProperty(value = "系统版本")
    private Integer version;


}
