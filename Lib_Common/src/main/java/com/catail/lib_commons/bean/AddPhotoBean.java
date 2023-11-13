package com.catail.lib_commons.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class AddPhotoBean implements MultiItemEntity, Serializable {
    public static final int Item_Type0 = 0;
    public static final int Item_Type1 = 1;
    private int itemType;
    private String pic;//图片本地的地址.
    private String photoTitle;
    private String net_pic;//网络上的地址

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPhotoTitle() {
        return photoTitle;
    }

    public void setPhotoTitle(String photoTitle) {
        this.photoTitle = photoTitle;
    }

    public String getNet_pic() {
        return net_pic;
    }

    public void setNet_pic(String net_pic) {
        this.net_pic = net_pic;
    }

    @Override
    public String toString() {
        return "AddPhotoBean{" +
                "pic='" + pic + '\'' +
                ", photoTitle='" + photoTitle + '\'' +
                ", itemType=" + itemType +
                '}';
    }
}
