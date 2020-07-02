package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdMenu;
import cn.dreamdeck.model.iot.vo.DdMenuVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */
public interface DdMenuService extends IService<DdMenu> {

    List<DdMenuVo> listMenuVo();


}
