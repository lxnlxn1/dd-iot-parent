package cn.dreamdeck.iot.service.impl;

import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.minio.minio.service.MinioTemplate;
import cn.dreamdeck.iot.mapper.DdProjectDocMapper;
import cn.dreamdeck.iot.service.DdProjectDocService;
import cn.dreamdeck.iot.service.DdProjectService;
import cn.dreamdeck.iot.service.SysDeviceModelService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.model.iot.DdProjectDoc;
import cn.dreamdeck.model.iot.SysDeviceModel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

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
    private  MinioTemplate minioTemplate;

    @Autowired
    private DdProjectService projectService;

    @Autowired
    private SysDeviceModelService sysDeviceModelService;


    //上传文件
    @Override
    public String saveFile(String projectId, String projectDocTypeId, MultipartFile file) {
        String bucketName = "project";

        List<DdProjectDoc> ddProjectDocs = baseMapper.selectList(new QueryWrapper<DdProjectDoc>().eq("project_id", projectId).like("project_doc_name", file.getResource().getFilename()));
        if (ddProjectDocs.size()!=0){
            return  "文件重复，请删除后添加";
        }

        try {
            DdProject ddProject = projectService.getById(projectId);
            InputStream inputStream = file.getInputStream();
            minioTemplate.putObject(bucketName, ddProject.getProjectName() + "-" + file.getResource().getFilename(), inputStream,file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            DdProjectDoc ddProjectDoc = new DdProjectDoc();
            ddProjectDoc.setProjectId(Integer.valueOf(projectId));
            ddProjectDoc.setProjectDocName(file.getResource().getFilename());
            ddProjectDoc.setProjectDocType(Integer.valueOf(projectDocTypeId));
            // String fileUrl = getFileUrl(bucketName, ddProject.getProjectName() + file.getName(), 7);
            //ddProjectDoc.setProjectDocUrl(fileUrl);
            ddProjectDoc.setCreateTime(DateUtil.getTime());
            ddProjectDoc.setBucketName(bucketName);
            ddProjectDoc.setStatus(0);
            baseMapper.insert(ddProjectDoc);

            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            return  "上传失败";
        }
    }

    private String getFileUrl(String bucketName, String s, int i) {
        return minioTemplate.getObjectURL(bucketName, s, i);
    }



//    @Override
//    public InputStream getProjectDocByName(String fileName) {
//
//       // InputStream file = minioTemplate.getFile(fileName);
//
//      //  return file;
//    }

    @Override
    public String uploadProjectDocByName(String projectDocId) {
        DdProjectDoc ddProjectDoc = baseMapper.selectById(projectDocId);

        String bucketName = ddProjectDoc.getBucketName();
        String fileUrl = null;
        if ("device".equals(bucketName)) {
            SysDeviceModel deviceModel = sysDeviceModelService.getById(ddProjectDoc.getModelId());
            fileUrl = getFileUrl(bucketName, deviceModel.getModelName() + "-" + ddProjectDoc.getProjectDocName(), 60 * 60 * 24);
        } else {
            DdProject project = projectService.getById(ddProjectDoc.getProjectId());
            fileUrl = getFileUrl(bucketName, project.getProjectName() + "-" + ddProjectDoc.getProjectDocName(), 60 * 60 * 24);
        }

        return fileUrl;
    }


    @Transactional
    @Override
    public boolean saveFileDevice(String modelId, MultipartFile file) {
        String bucketName = "device";
        SysDeviceModel deviceModel = sysDeviceModelService.getById(modelId);
        try {

            InputStream inputStream = file.getInputStream();
            minioTemplate.putObject(bucketName, deviceModel.getModelName() + "-" + file.getResource().getFilename(), inputStream,file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
            DdProjectDoc ddProjectDoc = new DdProjectDoc();
            ddProjectDoc.setProjectDocName(file.getResource().getFilename());
            ddProjectDoc.setCreateTime(DateUtil.getTime());
            ddProjectDoc.setBucketName(bucketName);
            ddProjectDoc.setStatus(0);
            ddProjectDoc.setModelId(modelId);
            baseMapper.insert(ddProjectDoc);

            DdProjectDoc ddProjectDoc1 = baseMapper.selectOne(new QueryWrapper<DdProjectDoc>().eq("model_id", modelId).like("bucket_name", bucketName));

            SysDeviceModel deviceModel1 = sysDeviceModelService.getById(modelId);
            deviceModel1.setDocId(ddProjectDoc1.getProjectDocId());
            sysDeviceModelService.updateById(deviceModel1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean saveBucket(String bucketName) {
        return minioTemplate.createBucket(bucketName);

    }

    @Override
    public boolean delProjectDoc(String projectDocId)  {

        DdProjectDoc ddProjectDoc = baseMapper.selectById(projectDocId);
        DdProject project = projectService.getById(ddProjectDoc.getProjectId());

        try {
            minioTemplate.removeObject(ddProjectDoc.getBucketName(),project.getProjectName() + "-" + ddProjectDoc.getProjectDocName());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

//    @Override
//    public void uploadProjectDocStreamByDocId(HttpServletRequest request, HttpServletResponse response, String projectDocId) {
//        DdProjectDoc ddProjectDoc = baseMapper.selectById(projectDocId);
//
//        String bucketName = ddProjectDoc.getBucketName();
//        InputStream inputStream = null;
//        if ("device".equals(bucketName)) {
//            SysDeviceModel deviceModel = sysDeviceModelService.getById(ddProjectDoc.getModelId());
//            inputStream    = minioTemplate.getObject(bucketName, deviceModel.getModelName() + "-" + ddProjectDoc.getProjectDocName());
//        } else {
//            DdProject project = projectService.getById(ddProjectDoc.getProjectId());
//            inputStream =  minioTemplate.getObject(bucketName, project.getProjectName() + "-" + ddProjectDoc.getProjectDocName());
//        }
//
//
//        return inputStream;
//
//
//    }

}
