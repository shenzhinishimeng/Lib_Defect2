package com.catail.lib_commons.interfaces;

public class AccountManager {

    public OnLoginFinishCallback onLoginFinishCallback;

    public void registerCallback(OnLoginFinishCallback callback) {
        this.onLoginFinishCallback = callback;
    }

    public void unregisterCallback() {
        this.onLoginFinishCallback = null;
    }


    // 1. 构造器私有化 外部能new
    public AccountManager() {
    }

    // 2. 本类内部创建对象实例
    private final static AccountManager instance = new AccountManager();

    // 3. 提供一个公有的静态方法，返回实例对象
    public static AccountManager getInstance() {
        return instance;
    }

}