package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.iot.mapper.DdProjectDocMapper;
import cn.dreamdeck.iot.minio.MinioTemplate;
import cn.dreamdeck.iot.service.DdDocTypeService;
import cn.dreamdeck.iot.service.DdProjectDocService;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.model.iot.DdDocType;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectDoc;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lxn
 * @since 2020-06-18
 */
@Service
public class DdProjectDocServiceImpl extends ServiceImpl<DdProjectDocMapper, DdProjectDoc> implements DdProjectDocService {


    @Autowired
    private MinioTemplate minioTemplate;

    @Autowired
    private DdProjectService projectService;

    @Autowired
    private DdDocTypeService ddDocTypeService;

    //上传文件
    @Override
    public boolean saveFile(String projectId, String projectDocTypeId, MultipartFile file) {

        try {
            String uploadFile = minioTemplate.uploadFile(file);
            DdProjectDoc ddProjectDoc = new DdProjectDoc();
            ddProjectDoc.setProjectId(Integer.valueOf(projectId));
            ddProjectDoc.setProjectDocName(file.getName());
            ddProjectDoc.setProjectDocType(Integer.valueOf(projectDocTypeId));
            String fileUrl = minioTemplate.getFileUrl(file.getName());
            ddProjectDoc.setProjectDocUrl(fileUrl);
            ddProjectDoc.setCreateTime(DateUtil.getTime());
            ddProjectDoc.setStatus(0);
            baseMapper.insert(ddProjectDoc);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public InputStream getProjectDocByName(String fileName) {

        InputStream file = minioTemplate.getFile(fileName);

        return file;
    }

    @Override
    public void uploadProjectDocByName(HttpServletRequest request, HttpServletResponse response, String projectDocId) {
        DdProjectDoc ddProjectDoc = baseMapper.selectById(projectDocId);
        DdProject ddProject = projectService.getById(ddProjectDoc.getProjectId());
        DdDocType ddDocType = ddDocTypeService.getById(ddProjectDoc.getProjectDocType());
        String name = ddProjectDoc.getProjectDocName();
        String newName = ddProject.getProjectName() + "-" + ddDocType.getDocTypeName() + "-" + name + DateUtil.getDay();
        minioTemplate.lookUploadFile(request, response, name, newName);
    }

}
