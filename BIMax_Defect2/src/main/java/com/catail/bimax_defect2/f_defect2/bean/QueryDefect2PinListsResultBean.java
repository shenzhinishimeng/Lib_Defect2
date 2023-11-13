package com.catail.bimax_defect2.f_defect2.bean;

import java.util.List;

public class QueryDefect2PinListsResultBean {

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
        private String drawing_position;
        private String defect_no;
        private String status;
        private String check_id;

        public String getDrawing_position() {
            return drawing_position;
        }

        public void setDrawing_position(String drawing_position) {
            this.drawing_position = drawing_position;
        }

        public String getDefect_no() {
            return defect_no;
        }

        public void setDefect_no(String defect_no) {
            this.defect_no = defect_no;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCheck_id() {
            return check_id;
        }

        public void setCheck_id(String check_id) {
            this.check_id = check_id;
        }
    }
}
