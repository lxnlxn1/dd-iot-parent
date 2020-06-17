package cn.dreamdeck.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lxn
 * @since 2020-06-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="用户表")
public class SysUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键ID")
	@TableId(value = "user_id", type = IdType.AUTO)
	private Integer userId;

	private String oldId;

	private Integer companyId;

	private String username;

	private String password;

	private String salt;

	private String phone;

	private String avatar;

	private String sex;

	@ApiModelProperty(value = "部门ID")
	private Integer deptId;

	@ApiModelProperty(value = "入职日期")
	private String joinedTime;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "修改时间")
	private Date updateTime;

	private String lockFlag;

	private String delFlag;

	private String wxOpenid;

	private String qqOpenid;

	private String giteeLogin;

	private String oscId;

	@ApiModelProperty(value = "所属租户")
	private Integer tenantId;


}
