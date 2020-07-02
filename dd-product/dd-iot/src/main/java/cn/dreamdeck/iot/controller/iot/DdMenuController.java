package cn.dreamdeck.iot.controller.iot;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.service.DdMenuService;
import cn.dreamdeck.model.iot.vo.DdMenuVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */
@RestController
@RequestMapping("/iot/menu")
public class DdMenuController {

    @Autowired
    private DdMenuService ddMenuService;

    @ApiOperation(value = "返回全部菜单列表")
    @GetMapping("/getAllMenu")
    public DdResult getAllMenu() {

        List<DdMenuVo> ddMenuList = ddMenuService.listMenuVo();

        if (ddMenuList == null && ddMenuList.size() <= 0) {
            return DdResult.fail("服务器错误");
        }
        return DdResult.ok(ddMenuList);
    }

}

