package com.catail.lib_commons.bean;

/**
 * Created by D on 2018/4/13.
 */

public class LoginResultBean {
    private int errno;
    private String errstr;
    private String errstr_cn;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String uid;
        private String token;

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


        @Override
        public String toString() {
            return "ResultBean{" +
                    "uid='" + uid + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResultBean{" +
                "errno=" + errno +
                ", errstr='" + errstr + '\'' +
                ", errstr_cn='" + errstr_cn + '\'' +
                ", result=" + result +
                '}';
    }
}
