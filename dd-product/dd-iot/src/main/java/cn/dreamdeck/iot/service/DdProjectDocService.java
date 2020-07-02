package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdProjectDoc;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-18
 */
public interface DdProjectDocService extends IService<DdProjectDoc> {

    String saveFile(String projectId, String projectDocTypeId, MultipartFile file);

   // InputStream getProjectDocByName(String fileName);

    String uploadProjectDocByName( String projectDocId);

    boolean saveFileDevice(String modelId, MultipartFile file);

    boolean saveBucket(String bucketName);

    boolean delProjectDoc(String projectDocId);

}
