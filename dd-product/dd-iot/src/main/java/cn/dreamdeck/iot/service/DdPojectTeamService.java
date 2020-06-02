package cn.dreamdeck.iot.service;


import cn.dreamdeck.model.iot.DdProjectTeam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DdPojectTeamService extends IService<DdProjectTeam> {

    /**
     * 根据用户id查询所属项目后列表
     */
    List<DdProjectTeam> list(Integer userId);

    /**
     * 分页列表
     *
     * @param page
     * @param ddProjectTeam
     * @return
     */
    IPage getProjectPage(Page page, DdProjectTeam ddProjectTeam);

    /**
     * 添加分组
     *
     * @param ddProjectTeam
     * @return
     */
    Boolean saveProject(DdProjectTeam ddProjectTeam);

    /**
     * 修改分组
     *
     * @param ddProjectTeam
     * @return
     */
    Boolean editBrand(DdProjectTeam ddProjectTeam);

    /**
     * 删除分组
     *
     * @param ddProjectTeam
     * @return
     */
    Boolean deleteBrand(DdProjectTeam ddProjectTeam);

}
