package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDisciplineListsResultBean {

    private Integer errno;
    private String errstr;
    private String errstr_cn;
    private List<ResultBean> result;

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
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

    public static class ResultBean {
        private String discipline;
        private Integer discipline_id;

        public String getDiscipline() {
            return discipline;
        }

        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }

        public Integer getDiscipline_id() {
            return discipline_id;
        }

        public void setDiscipline_id(Integer discipline_id) {
            this.discipline_id = discipline_id;
        }
    }
}
