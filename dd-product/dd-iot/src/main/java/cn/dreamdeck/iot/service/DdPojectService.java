package cn.dreamdeck.iot.service;


import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.model.iot.DdProject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DdPojectService extends IService<DdProject> {

    List<DdProject> list(DdProject ddProject);


    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    DdResult uploadFile(MultipartFile file);


    /**
     * 分页列表
     *
     * @param page
     * @param ddProject
     * @return
     */
    IPage getProject(Page page, DdProject ddProject);

    /**
     * 添加分组
     *
     * @param ddProject
     * @return
     */
    Boolean saveProject(DdProject ddProject);

    /**
     * 修改分组
     *
     * @param ddProject
     * @return
     */
    Boolean editProject(DdProject ddProject);

    /**
     * 删除分组
     *
     * @param ddProject
     * @return
     */
    Boolean deleteProject(DdProject ddProject);

}
