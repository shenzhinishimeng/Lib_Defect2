package com.catail.lib_commons.interfaces;

public interface BESCallBack {
    void onSuccess(String name, Object objectBean);

    void onFaild(String name, String faildStr);
}
