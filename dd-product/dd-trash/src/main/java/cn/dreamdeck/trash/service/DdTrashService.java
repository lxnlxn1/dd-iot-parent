package cn.dreamdeck.trash.service;

import cn.dreamdeck.model.trash.DdTrash;
import com.baomidou.mybatisplus.extension.service.IService;


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
    String openTrash(String trashId, String num);


    //状态
    String status(String trashId);

    //添加到设备列表
    boolean saveDevice(DdTrash ddTrash);

    String openNum(String trashId);

    boolean updateDevice(String ip);

    String openData(int id, int num);


    DdTrash selectByauiaModel(String model);

    String satisfaction(String trashId);
}
