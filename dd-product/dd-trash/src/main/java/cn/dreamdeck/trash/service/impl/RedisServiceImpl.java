//package cn.dreamdeck.trash.service.impl;
//
//
//import cn.dreamdeck.model.trash.DdTrash;
//import cn.dreamdeck.service.constant.RedisConst;
//import cn.dreamdeck.trash.service.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class RedisServiceImpl implements RedisService {
//
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    public DdTrash getTrashData(Integer id) {
//
//        DdTrash trashModel = null;
//        try {
//            trashModel = (DdTrash) redisTemplate.opsForValue().get(RedisConst.DEVICE_TRASH_DATA + id);
//        } catch (Exception e) {
//            if (trashModel == null) {
//                trashModel = new DdTrash();
//            }
//        }
//
//        return trashModel;
//    }
//
//    @Override
//    public void saveTrashData(DdTrash trashModel) {
//        redisTemplate.opsForValue().set(RedisConst.DEVICE_TRASH_DATA + trashModel.getDeviceId(), trashModel);
//    }
//
//    @Override
//    public void saveTrashNum(Map<String, Integer> map,String ip) {
//        redisTemplate.opsForHash().putAll(RedisConst.DEVICE_TRASH_NUM+ip,map);
//    }
//
//    @Override
//    public Map<String, Integer> getTrashNum(String ip) {
//        Map<String,Integer> resultMap= redisTemplate.opsForHash().entries(RedisConst.DEVICE_TRASH_NUM+ip);
//        if (resultMap == null) {
//            resultMap = new HashMap<>();
//        }
//        return resultMap;
//    }
//
//
//}
