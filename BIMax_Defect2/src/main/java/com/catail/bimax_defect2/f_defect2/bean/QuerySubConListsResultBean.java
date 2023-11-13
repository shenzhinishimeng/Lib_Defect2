package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QuerySubConListsResultBean {

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
        private Integer contractor_id;
        private String contractor_name;

        public Integer getContractor_id() {
            return contractor_id;
        }

        public void setContractor_id(Integer contractor_id) {
            this.contractor_id = contractor_id;
        }

        public String getContractor_name() {
            return contractor_name;
        }

        public void setContractor_name(String contractor_name) {
            this.contractor_name = contractor_name;
        }
    }
}
