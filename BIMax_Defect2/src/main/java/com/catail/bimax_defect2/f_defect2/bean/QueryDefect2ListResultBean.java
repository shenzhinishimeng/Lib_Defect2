package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDefect2ListResultBean {

    private String errstr_cn;
    private String errstr;
    private List<ResultBean> result;
    private Integer errno;

    public String getErrstr_cn() {
        return errstr_cn;
    }

    public void setErrstr_cn(String errstr_cn) {
        this.errstr_cn = errstr_cn;
    }

    public String getErrstr() {
        return errstr;
    }

    public void setErrstr(String errstr) {
        this.errstr = errstr;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public static class ResultBean {
        private String apply_time;
        private String update_time;
        private String trade;
        private String user_name;
        private String short_name;
        private String status;
        private String block;
        private String sub_con_name;
        private String secondary_region;
        private String category;
        private String discipline;
        private String location;
        private String defect_no;
        private String check_id;

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCheck_id() {
            return check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getApply_time() {
            return apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = apply_time;
        }

        public String getTrade() {
            return trade;
        }

        public void setTrade(String trade) {
            this.trade = trade;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getSub_con_name() {
            return sub_con_name;
        }

        public void setSub_con_name(String sub_con_name) {
            this.sub_con_name = sub_con_name;
        }

        public String getSecondary_region() {
            return secondary_region;
        }

        public void setSecondary_region(String secondary_region) {
            this.secondary_region = secondary_region;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDiscipline() {
            return discipline;
        }

        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getDefect_no() {
            return defect_no;
        }

        public void setDefect_no(String defect_no) {
            this.defect_no = defect_no;
        }
    }
}
