package cn.dreamdeck.iot.synchronization.Locale;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 项目属性
 *
 * @author lxn
 *
 */
@SuppressWarnings("serial")

@Data
public class Receive implements Serializable {

    private String code;
    private String msg;//信息
    private List<RojectRVo> records;//数据




}