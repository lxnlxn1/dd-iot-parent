package cn.dreamdeck.iot.service.impl;


import cn.dreamdeck.iot.mapper.SysDictDataMapper;
import cn.dreamdeck.iot.service.SysDictDataService;
import cn.dreamdeck.model.iot.SysDictData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictData> implements SysDictDataService {
    @Override
    public List<SysDictData> list(Integer dictId) {
        return this.baseMapper.selectList(  new QueryWrapper<SysDictData>().eq("dict_id", dictId));
    }

    @Override
    public SysDictData selectById(String dictCode) {
        return this.baseMapper.selectById(dictCode);
    }
}
