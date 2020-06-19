package cn.dreamdeck.model.iot.vo;


import cn.dreamdeck.model.iot.DdMenu;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="权限", description="权限")
public class DdProjectRoleVo {

    private String userId;

    private List<DdMenu> ddMenus;

}
