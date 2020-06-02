package cn.dreamdeck.model.iot.vo;


import cn.dreamdeck.model.iot.DdDeviceGatewayCom;
import cn.dreamdeck.model.iot.DdDeviceGetway;

import java.util.List;

public class DdDeviceGetwayModel extends DdDeviceGetway {

    private List<DdDeviceGatewayCom> list;

    public List<DdDeviceGatewayCom> getList() {
        return list;
    }

    public void setList(List<DdDeviceGatewayCom> list) {
        this.list = list;
    }
}
