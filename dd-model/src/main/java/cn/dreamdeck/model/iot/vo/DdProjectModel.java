package cn.dreamdeck.model.iot.vo;


import cn.dreamdeck.model.iot.DdProjectTeam;

public class DdProjectModel extends DdProjectTeam {


    private String projectName;

    private String projectImg;

    public String getProjectImg() {
        return projectImg;
    }

    public void setProjectImg(String projectImg) {
        this.projectImg = projectImg;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
