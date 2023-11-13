package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDefect2StatisticsTypePieChartListsResult {

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
        private Integer cnt;
        private String name;
        private String color;

        public Integer getCnt() {
            return cnt;
        }

        public void setCnt(Integer cnt) {
            this.cnt = cnt;
        }

        public String getCategory() {
            return name;
        }

        public void setCategory(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
