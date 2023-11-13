package com.catail.lib_commons.bean;

/**
 * Created by D on 2018/4/13.
 */

public class LoginRequestBean {
    private String phone;
    private String pwd;
    private String device_info;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }
}
