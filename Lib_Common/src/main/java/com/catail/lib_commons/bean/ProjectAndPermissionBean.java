package com.catail.lib_commons.bean;

import java.io.Serializable;

public class ProjectAndPermissionBean implements Serializable {
    private String project_id;
    private String project_name;
    private String program_pic;
    private String app_models;
    private String is_lite;

    private String group_id;
    private String group_type;

    private String qa_insp_vision;//inspection 1.0 还是2.0版本
    //    private String acci_mode;//意外的类型
//    private String ins_mode;//安全检查的类型
//    private String ptw_mode;//ptw的类型
//    private String tbm_mode;//tbm的类型
//    private String train_mode;//培训的类型

    private String location_require;//需要位置采集 PTW
//    private String tbm_declare; //tbm 声明 要不要显示

    private String qa_clt_review;    //—qa-checklist 是否显示review选项：0不显示；1显示；
    private String qa_task_showtime;//qa_task_showtime 1显示；0不显示

    private String bes_company_id;
    private String bes_project_id;

    public String getBes_company_id() {
        return bes_company_id;
    }

    public void setBes_company_id(String bes_company_id) {
        this.bes_company_id = bes_company_id;
    }

    public String getBes_project_id() {
        return bes_project_id;
    }

    public void setBes_project_id(String bes_project_id) {
        this.bes_project_id = bes_project_id;
    }

    public String getQa_insp_vision() {
        return qa_insp_vision;
    }

    public void setQa_insp_vision(String qa_insp_vision) {
        this.qa_insp_vision = qa_insp_vision;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }

    public String getIs_lite() {
        return is_lite;
    }

    public void setIs_lite(String is_lite) {
        this.is_lite = is_lite;
    }

    public String getProgram_pic() {
        return program_pic;
    }

    public void setProgram_pic(String program_pic) {
        this.program_pic = program_pic;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

//    public String getAcci_mode() {
//        return acci_mode;
//    }
//
//    public void setAcci_mode(String acci_mode) {
//        this.acci_mode = acci_mode;
//    }
//
//    public String getIns_mode() {
//        return ins_mode;
//    }
//
//    public void setIns_mode(String ins_mode) {
//        this.ins_mode = ins_mode;
//    }
//
//    public String getPtw_mode() {
//        return ptw_mode;
//    }
//
//    public void setPtw_mode(String ptw_mode) {
//        this.ptw_mode = ptw_mode;
//    }
//
//    public String getTbm_mode() {
//        return tbm_mode;
//    }
//
//    public void setTbm_mode(String tbm_mode) {
//        this.tbm_mode = tbm_mode;
//    }
//
//    public String getTrain_mode() {
//        return train_mode;
//    }
//
//    public void setTrain_mode(String train_mode) {
//        this.train_mode = train_mode;
//    }

    public String getLocation_require() {
        return location_require;
    }

    public void setLocation_require(String location_require) {
        this.location_require = location_require;
    }

//    public String getTbm_declare() {
//        return tbm_declare;
//    }
//
//    public void setTbm_declare(String tbm_declare) {
//        this.tbm_declare = tbm_declare;
//    }

    public String getQa_clt_review() {
        return qa_clt_review;
    }

    public void setQa_clt_review(String qa_clt_review) {
        this.qa_clt_review = qa_clt_review;
    }


    public String getQa_task_showtime() {
        return qa_task_showtime;
    }

    public void setQa_task_showtime(String qa_task_showtime) {
        this.qa_task_showtime = qa_task_showtime;
    }

    public String getApp_models() {
        return app_models;
    }

    public void setApp_models(String app_models) {
        this.app_models = app_models;
    }
}
