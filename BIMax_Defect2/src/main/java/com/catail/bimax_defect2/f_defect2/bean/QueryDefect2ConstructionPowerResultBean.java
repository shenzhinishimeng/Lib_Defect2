package com.catail.bimax_defect2.f_defect2.bean;

public class QueryDefect2ConstructionPowerResultBean {
    private String errstr_cn;
    private String errstr;
    private ResultBean result;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public static class ResultBean {
        private String apply;
        private String draft_label;

        public String getApply() {
            return apply;
        }

        public void setApply(String apply) {
            this.apply = apply;
        }

        public String getDraft_label() {
            return draft_label;
        }

        public void setDraft_label(String draft_label) {
            this.draft_label = draft_label;
        }
    }
}
