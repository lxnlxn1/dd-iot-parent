package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.iot.mapper.DdMenuMapper;
import cn.dreamdeck.iot.service.DdMenuService;
import cn.dreamdeck.model.iot.DdMenu;
import cn.dreamdeck.model.iot.vo.DdMenuVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-19
 */
@Service
public class DdMenuServiceImpl extends ServiceImpl<DdMenuMapper, DdMenu> implements DdMenuService {


    /**
     * 递归菜单
     *
     * @return
     */
    @Override
    public List<DdMenuVo> listMenuVo() {

        List<DdMenu> ddMenuList = baseMapper.selectList(new QueryWrapper<DdMenu>());


        List<DdMenuVo> trees = new ArrayList<>();
        for (DdMenu ddMenu : ddMenuList) {
            if (ddMenu.getParentId() == -1) {
                DdMenuVo ddMenuVo1 = new DdMenuVo();
                ddMenuVo1.setId(ddMenu.getMenuId());
                ddMenuVo1.setLabel(ddMenu.getName());
                trees.add(setMenu(ddMenuVo1, ddMenuList));
            }
        }

        return trees;
    }



    /**
     * 递归添加子节点
     *
     * @param ddMenuVo1
     * @param ddMenuList
     * @return
     */
    private DdMenuVo setMenu(DdMenuVo ddMenuVo1, List<DdMenu> ddMenuList) {

        ddMenuVo1.setChildren(new ArrayList<DdMenuVo>());
        for (DdMenu ddMenu : ddMenuList) {



//            System.out.println(ddMenuVo1.getMenuId());
//            System.out.println(ddMenuVo.getParentId());
            if (ddMenuVo1.getId().equals(ddMenu.getParentId())) {
                DdMenuVo ddMenuVo = new DdMenuVo();
                ddMenuVo.setId(ddMenu.getMenuId());
                ddMenuVo.setLabel(ddMenu.getName());


                if (ddMenuVo1.getChildren() == null) {
                    ddMenuVo1.setChildren(new ArrayList<>());
                }
                ddMenuVo1.getChildren().add(setMenu(ddMenuVo, ddMenuList));
            }
        }
        return ddMenuVo1;
    }

//
//    /**
//     * 递归菜单
//     *
//     * @return
//     */
//    @Override
//    public List<DdMenuVo> listMenuVo() {
//
//        List<DdMenu> ddMenuList = baseMapper.selectList(new QueryWrapper<DdMenu>());
//
//
//        List<DdMenuVo> trees = new ArrayList<>();
//        for (DdMenu ddMenu : ddMenuList) {
//            if (ddMenu.getParentId() == -1) {
//                DdMenuVo ddMenuVo1 = new DdMenuVo();
//                BeanUtils.copyProperties(ddMenu, ddMenuVo1);
//                ddMenuVo1.setSort(1);
//                trees.add(setMenu(ddMenuVo1, ddMenuList));
//            }
//        }
//
//        return trees;
//    }
//
//    /**
//     * 递归添加子节点
//     *
//     * @param ddMenuVo1
//     * @param ddMenuList
//     * @return
//     */
//    private DdMenuVo setMenu(DdMenuVo ddMenuVo1, List<DdMenu> ddMenuList) {
//
//        ddMenuVo1.setDdMenuVoList(new ArrayList<DdMenuVo>());
//        for (DdMenu ddMenu : ddMenuList) {
//            DdMenuVo ddMenuVo = new DdMenuVo();
//            BeanUtils.copyProperties(ddMenu, ddMenuVo);
//
//
////            System.out.println(ddMenuVo1.getMenuId());
////            System.out.println(ddMenuVo.getParentId());
//            if (ddMenuVo1.getMenuId().equals(ddMenuVo.getParentId())) {
//                int sort = ddMenuVo1.getSort() + 1;
//                ddMenuVo.setSort(sort);
//                if (ddMenuVo1.getDdMenuVoList() == null) {
//                    ddMenuVo1.setDdMenuVoList(new ArrayList<>());
//                }
//                ddMenuVo1.getDdMenuVoList().add(setMenu(ddMenuVo, ddMenuList));
//            }
//        }
//
//        return ddMenuVo1;
//    }


}
