package cn.dreamdeck.trash.service;


import cn.dreamdeck.model.trash.DdTrash;

import java.util.Map;

public interface RedisService {

    DdTrash getTrashData(String ip);

    void saveTrashData(DdTrash tm);

    void saveTrashNum(Map<String, Integer> map, String ip);

    Map<String, Integer> getTrashNum(String ip);
}
