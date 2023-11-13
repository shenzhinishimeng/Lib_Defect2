package com.catail.lib_commons.bean;

public class DataSuccessBean {
    private String errorNum;
    private boolean isSuccess;
    private String errStr;
    private String ettstrCn;
    private String file_name;

    public String getEttstrCn() {
        return ettstrCn;
    }

    public void setEttstrCn(String ettstrCn) {
        this.ettstrCn = ettstrCn;
    }

    public String getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(String errorNum) {
        this.errorNum = errorNum;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrStr() {
        return errStr;
    }

    public void setErrStr(String errStr) {
        this.errStr = errStr;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Override
    public String toString() {
        return "DataSuccessBean{" +
                "errorNum='" + errorNum + '\'' +
                ", isSuccess=" + isSuccess +
                ", errStr='" + errStr + '\'' +
                ", ettstrCn='" + ettstrCn + '\'' +
                ", file_name='" + file_name + '\'' +
                '}';
    }
}
