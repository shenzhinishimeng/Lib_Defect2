package com.catail.bimax_defect2.f_defect2.bean;

public class QueryDefect2StatisticsListsRequestBean {
    private String uid;
    private String token;
    private String project_id;
    private String bes_project_id;
    private String defect_type;
    private String flag;

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

    public String getBes_project_id() {
        return bes_project_id;
    }

    public void setBes_project_id(String bes_project_id) {
        this.bes_project_id = bes_project_id;
    }

    public String getDefect_type() {
        return defect_type;
    }

    public void setDefect_type(String defect_type) {
        this.defect_type = defect_type;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
