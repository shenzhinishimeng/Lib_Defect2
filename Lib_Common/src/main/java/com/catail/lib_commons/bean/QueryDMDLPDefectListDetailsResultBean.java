package com.catail.lib_commons.bean;

import java.io.Serializable;
import java.util.List;

public class QueryDMDLPDefectListDetailsResultBean {
    private int errno;
    private String errstr;
    private String errstr_cn;
    private List<ResultBean> result;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrstr() {
        return errstr;
    }

    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    public String getErrstr_cn() {
        return errstr_cn;
    }

    public void setErrstr_cn(String errstr_cn) {
        this.errstr_cn = errstr_cn;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable {
        private String pic_path;
        private String creator_id;
        private String to_contractor_name;
        private int status;
        private String deadline_date;
        private String type_3;
        private String to_name;
        private String unread_flag;
        private String type_1;
        private String title;
        private String group;
        private String type_2;
        private int type_id;
        private String position;
        private String check_id;
        private String local_check_id;

        private String pin_pos;
        private float pin_x;
        private float pin_y;
        private int drawing_id;
        private String drawing_name;
        private String drawing_path;
        private String drawing_position;
        private String zone;
        private String record_type;//0本地数据 1网络数据
        private String drawing_version;//图纸的版本号.

        private String feature_pin_type;//defect,inspection(如果不写或者没有就是inspection)

        public String getFeature_pin_type() {
            return feature_pin_type;
        }

        public void setFeature_pin_type(String feature_pin_type) {
            this.feature_pin_type = feature_pin_type;
        }

        public String getRecord_type() {
            return record_type;
        }

        public void setRecord_type(String record_type) {
            this.record_type = record_type;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public String getDrawing_position() {
            return drawing_position;
        }

        public void setDrawing_position(String drawing_position) {
            this.drawing_position = drawing_position;
        }

        public float getPin_x() {
            return pin_x;
        }

        public void setPin_x(float pin_x) {
            this.pin_x = pin_x;
        }

        public float getPin_y() {
            return pin_y;
        }

        public void setPin_y(float pin_y) {
            this.pin_y = pin_y;
        }

        public int getDrawing_id() {
            return drawing_id;
        }

        public void setDrawing_id(int drawing_id) {
            this.drawing_id = drawing_id;
        }

        public String getDrawing_name() {
            return drawing_name;
        }

        public void setDrawing_name(String drawing_name) {
            this.drawing_name = drawing_name;
        }

        public String getDrawing_path() {
            return drawing_path;
        }

        public void setDrawing_path(String drawing_path) {
            this.drawing_path = drawing_path;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getCreator_id() {
            return creator_id;
        }

        public void setCreator_id(String creator_id) {
            this.creator_id = creator_id;
        }

        public String getTo_contractor_name() {
            return to_contractor_name;
        }

        public void setTo_contractor_name(String to_contractor_name) {
            this.to_contractor_name = to_contractor_name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDeadline_date() {
            return deadline_date;
        }

        public void setDeadline_date(String deadline_date) {
            this.deadline_date = deadline_date;
        }

        public String getType_3() {
            return type_3;
        }

        public void setType_3(String type_3) {
            this.type_3 = type_3;
        }

        public String getTo_name() {
            return to_name;
        }

        public void setTo_name(String to_name) {
            this.to_name = to_name;
        }

        public String getUnread_flag() {
            return unread_flag;
        }

        public void setUnread_flag(String unread_flag) {
            this.unread_flag = unread_flag;
        }

        public String getType_1() {
            return type_1;
        }

        public void setType_1(String type_1) {
            this.type_1 = type_1;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getType_2() {
            return type_2;
        }

        public void setType_2(String type_2) {
            this.type_2 = type_2;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCheck_id() {
            return check_id;
        }

        public String getLocal_check_id() {
            return local_check_id;
        }

        public void setLocal_check_id(String local_check_id) {
            this.local_check_id = local_check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }


        public String getPin_pos() {
            return pin_pos;
        }

        public void setPin_pos(String pin_pos) {
            this.pin_pos = pin_pos;
        }

        public String getDrawing_version() {
            return drawing_version;
        }

        public void setDrawing_version(String drawing_version) {
            this.drawing_version = drawing_version;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "pic_path='" + pic_path + '\'' +
                    ", creator_id='" + creator_id + '\'' +
                    ", to_contractor_name='" + to_contractor_name + '\'' +
                    ", status=" + status +
                    ", deadline_date='" + deadline_date + '\'' +
                    ", type_3='" + type_3 + '\'' +
                    ", to_name='" + to_name + '\'' +
                    ", unread_flag='" + unread_flag + '\'' +
                    ", type_1='" + type_1 + '\'' +
                    ", title='" + title + '\'' +
                    ", group='" + group + '\'' +
                    ", type_2='" + type_2 + '\'' +
                    ", type_id=" + type_id +
                    ", position='" + position + '\'' +
                    ", check_id='" + check_id + '\'' +
                    ", pin_pos=" + pin_pos +
                    ", pin_x=" + pin_x +
                    ", pin_y=" + pin_y +
                    ", drawing_id=" + drawing_id +
                    ", drawing_name='" + drawing_name + '\'' +
                    ", drawing_path='" + drawing_path + '\'' +
                    ", drawing_position='" + drawing_position + '\'' +
                    ", drawing_version='" + drawing_version + '\'' +
                    ", zone='" + zone + '\'' +
                    ", record_type='" + record_type + '\'' +
                    '}';
        }
    }
}
