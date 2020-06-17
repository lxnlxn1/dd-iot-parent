package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdProjectTeam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-15
 */
public interface DdProjectTeamService extends IService<DdProjectTeam> {


    //根据用户id获取项目
    List<Integer> getProjectByUserId(String userId);

}
