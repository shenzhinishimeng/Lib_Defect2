package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDefect2CategoryListRequestBean {

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
        private String complete_cnt;
        private String total_cnt;
        private Integer category_id;
        private String defect_type;
        private String discipline_id;
        private String category;

        public String getComplete_cnt() {
            return complete_cnt;
        }

        public void setComplete_cnt(String complete_cnt) {
            this.complete_cnt = complete_cnt;
        }

        public String getTotal_cnt() {
            return total_cnt;
        }

        public void setTotal_cnt(String total_cnt) {
            this.total_cnt = total_cnt;
        }

        public Integer getCategory_id() {
            return category_id;
        }

        public void setCategory_id(Integer category_id) {
            this.category_id = category_id;
        }

        public String getDefect_type() {
            return defect_type;
        }

        public void setDefect_type(String defect_type) {
            this.defect_type = defect_type;
        }

        public String getDiscipline_id() {
            return discipline_id;
        }

        public void setDiscipline_id(String discipline_id) {
            this.discipline_id = discipline_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }
    }
}
