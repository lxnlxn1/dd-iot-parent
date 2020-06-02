package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.common.data.DateUtil;
import cn.dreamdeck.common.result.DdResult;
import cn.dreamdeck.iot.mapper.DdProjectMapper;
import cn.dreamdeck.iot.minio.MinioTemplate;
import cn.dreamdeck.iot.service.DdPojectService;
import cn.dreamdeck.model.iot.DdProject;
import cn.dreamdeck.service.util.CommonConstants;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DdProjectServiceImpl extends ServiceImpl<DdProjectMapper, DdProject> implements DdPojectService {
    private final MinioTemplate minioTemplate;

    @Override
    public List<DdProject> list(DdProject ddProject) {
        return this.baseMapper.selectList(new QueryWrapper<DdProject>().eq("status",CommonConstants.STATUS_NORMAL));
    }

    @Override
    public DdResult uploadFile(MultipartFile file) {
        String fileName = DateUtil.getDays();
        if (StringUtils.isNotBlank(file.getOriginalFilename())) {
            fileName += IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        } else {
            fileName += IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getName());
        }
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("bucketName", "picture");
        resultMap.put("fileName", fileName);

        try {
            minioTemplate.uploadFile(file.getInputStream(),fileName);
            //文件管理数据记录,收集管理追踪文件
            //fileLog(file, fileName);
        } catch (Exception e) {
            log.error("上传失败", e);
            return DdResult.fail(e.getLocalizedMessage());
        }
        return DdResult.ok(resultMap);
    }

    @Override
    public IPage getProject(Page page, DdProject ddProject) {
        return this.baseMapper.selectPage(page, new QueryWrapper<DdProject>().eq("status",CommonConstants.STATUS_NORMAL).orderByDesc("create_time"));
    }

    @Override
    public Boolean saveProject(DdProject ddProject) {
        this.baseMapper.insert(ddProject);
        return Boolean.TRUE;

    }


    @Override
    public Boolean editProject(DdProject ddProject) {
        this.baseMapper.updateById(ddProject);
        return Boolean.TRUE;

    }

    @Override
    public Boolean deleteProject(DdProject ddProject) {
        ddProject.setStatus(Integer.valueOf(CommonConstants.STATUS_DEL));
        this.updateById(ddProject);
        return Boolean.TRUE;
    }
}
