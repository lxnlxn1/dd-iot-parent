package cn.dreamdeck.user.synchronization.Oa;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
class RojectRUserVo {


    private Integer userId;

    private String oldId;

    private Integer companyId;

    private String username;

    private String password;

    @ApiModelProperty(value = "入职日期")
    private String joinedTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    private String delFlag;

    private String lockFlag;


    private String phone;

    private String avatar;

    private String sex;

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;


    @ApiModelProperty(value = "所属租户")
    private Integer tenantId;


    private String wxOpenid;

    private String qqOpenid;

    private String giteeLogin;

    private String oscId;


}
