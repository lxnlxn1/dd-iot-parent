package cn.dreamdeck.model.iot.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DdProjectList implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userList;

    private Integer projectId;
}
