package cn.dreamdeck.trash.service;

import cn.dreamdeck.model.trash.DdTrash;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-04
 */
public interface DdTrashService extends IService<DdTrash> {

    //开桶
    String openTrash(String ip, String num);


    //状态
    String status(String ip);

    //添加到设备列表
    boolean saveDevice(DdTrash ddTrash);

    Map<String, Integer> openNum(String ip);

    boolean updateDevice(String ip);

    String openData(int i, int i1);


    DdTrash selectByauiaModel(String model);
}
