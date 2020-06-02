package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.DdProjectDocMapper;
import cn.dreamdeck.iot.service.DdProjectDocService;
import cn.dreamdeck.model.iot.DdProjectDoc;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class DdProjectDocServiceImpl extends ServiceImpl<DdProjectDocMapper, DdProjectDoc> implements DdProjectDocService {
    @Override
    public List<DdProjectDoc> docList(Integer docType, Integer projectId) {

        return this.baseMapper.selectList(new QueryWrapper<DdProjectDoc>().eq("project_doc_type",docType).eq("project_id",projectId));
    }

    @Override
    public Boolean saveProjectDoc(DdProjectDoc ddProjectDoc) {
        this.baseMapper.insert(ddProjectDoc);

        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteProjectDoc(DdProjectDoc ddProjectDoc) {
        this.baseMapper.deleteById(ddProjectDoc);
        return Boolean.TRUE;
    }
}
