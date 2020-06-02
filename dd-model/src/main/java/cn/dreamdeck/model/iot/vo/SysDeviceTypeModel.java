package cn.dreamdeck.model.iot.vo;



import cn.dreamdeck.model.iot.SysDeviceType;
import cn.dreamdeck.model.iot.SysDictData;

import java.util.ArrayList;
import java.util.List;

public class SysDeviceTypeModel extends SysDeviceType {

    List<SysDictData> list = new ArrayList<>();

    public List<SysDictData> getList() {
        return list;
    }

    public void setList(List<SysDictData> list) {
        this.list = list;
    }
}
