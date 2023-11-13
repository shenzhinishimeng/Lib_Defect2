package com.catail.bimax_defect2.f_defect2.bean;

public class Defect2DrawingRequestBean {
    private String uid;
    private String token;
    private String project_id;

    private String drawing_flag;
    private String block;
    private String level;
    private String zone;
    private String drawing_id;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getDrawing_flag() {
        return drawing_flag;
    }

    public void setDrawing_flag(String drawing_flag) {
        this.drawing_flag = drawing_flag;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDrawing_id() {
        return drawing_id;
    }

    public void setDrawing_id(String drawing_id) {
        this.drawing_id = drawing_id;
    }
}
