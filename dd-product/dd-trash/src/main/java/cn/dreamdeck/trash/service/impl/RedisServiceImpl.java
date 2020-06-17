package cn.dreamdeck.trash.service.impl;


import cn.dreamdeck.model.trash.DdTrash;
import cn.dreamdeck.trash.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public DdTrash getTrashData(String deviceId) {

        DdTrash trashModel = null;
        try {
            trashModel = (DdTrash) redisTemplate.opsForValue().get("trash_data_" + deviceId);
        } catch (Exception e) {
            if (trashModel == null) {
                trashModel = new DdTrash();
            }
        }

        return trashModel;
    }

    @Override
    public void saveTrashData(DdTrash trashModel) {
        redisTemplate.opsForValue().set("trash_data_" + trashModel.getDeviceIp(), trashModel);
    }

    @Override
    public void saveTrashNum(Map<String, Integer> map,String ip) {
        redisTemplate.opsForHash().putAll("trash_num_"+ip,map);
    }

    @Override
    public Map<String, Integer> getTrashNum(String ip) {
        Map<String,Integer> resultMap= redisTemplate.opsForHash().entries("trash_num_"+ip);
        if (resultMap == null) {
            resultMap = new HashMap<>();
        }
        return resultMap;
    }


}
