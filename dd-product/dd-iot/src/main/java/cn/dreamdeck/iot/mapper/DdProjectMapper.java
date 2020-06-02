package cn.dreamdeck.iot.mapper;

import cn.dreamdeck.model.iot.DdProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface DdProjectMapper extends BaseMapper<DdProject> {
    /**
     * 通过用户ID，查询项目信息
     *
     * @param userId
     * @return
     */
    List<DdProject> listProjectByUserId(Integer userId);
}
