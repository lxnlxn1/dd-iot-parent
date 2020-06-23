package cn.dreamdeck.iot.service;

import cn.dreamdeck.model.iot.DdProjectDoc;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxn
 * @since 2020-06-18
 */
public interface DdProjectDocService extends IService<DdProjectDoc> {

    boolean saveFile(String projectId, String projectDocTypeId, MultipartFile file);

    InputStream getProjectDocByName(String fileName);

    void uploadProjectDocByName(HttpServletRequest request, HttpServletResponse response, String projectDocId);
}
