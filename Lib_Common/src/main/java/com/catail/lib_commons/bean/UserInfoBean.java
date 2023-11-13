package com.catail.lib_commons.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {
	private String phone;
	private String pwd;
	private String roleId;// 用户角色ID
	private String corpId;// 用户诉说承包商ID
	private String white_backgroundListType;// 是否为白名单用户 0：普通用户 1：白名单用户
	private String teamName;// 角色分组名称
	private String name;// 用户名
	private String teamNameEn;// 角色分组英文名称
	private String faceId;// 人脸识别标识
    private String face_img;
	private String roleName;// 角色名称
	private String teamId;// 角色分组ID
	private String corpName;// 承包商名称
	private String roleNameEn;// 角色英文名称

	private String contractor_type;
	private String contractor_name;
	private String userId;
	private String contractor_id;
	private String signaturePath;// 签名地址

	public String getContractor_id() {
		return contractor_id;
	}

	public void setContractor_id(String contractor_id) {
		this.contractor_id = contractor_id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContractor_name() {
		return contractor_name;
	}

	public void setContractor_name(String contractor_name) {
		this.contractor_name = contractor_name;
	}

	public String getContractor_type() {
		return contractor_type;
	}

	public void setContractor_type(String contractor_type) {
		this.contractor_type = contractor_type;
	}

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

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getwhite_backgroundListType() {
		return white_backgroundListType;
	}

	public void setwhite_backgroundListType(String white_backgroundListType) {
		this.white_backgroundListType = white_backgroundListType;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamNameEn() {
		return teamNameEn;
	}

	public void setTeamNameEn(String teamNameEn) {
		this.teamNameEn = teamNameEn;
	}

	public String getFaceId() {
		return faceId;
	}

	public void setFaceId(String faceId) {
		this.faceId = faceId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getRoleNameEn() {
		return roleNameEn;
	}

	public void setRoleNameEn(String roleNameEn) {
		this.roleNameEn = roleNameEn;
	}
	public String getSignaturePath() {
		return signaturePath;
	}

	public void setSignaturePath(String signaturePath) {
		this.signaturePath = signaturePath;
	}

    public String getFace_img() {
        return face_img;
    }

    public void setFace_img(String face_img) {
        this.face_img = face_img;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", roleId='" + roleId + '\'' +
                ", corpId='" + corpId + '\'' +
                ", white_backgroundListType='" + white_backgroundListType + '\'' +
                ", teamName='" + teamName + '\'' +
                ", name='" + name + '\'' +
                ", teamNameEn='" + teamNameEn + '\'' +
                ", faceId='" + faceId + '\'' +
                ", face_img='" + face_img + '\'' +
                ", roleName='" + roleName + '\'' +
                ", teamId='" + teamId + '\'' +
                ", corpName='" + corpName + '\'' +
                ", roleNameEn='" + roleNameEn + '\'' +
                ", contractor_type='" + contractor_type + '\'' +
                ", contractor_name='" + contractor_name + '\'' +
                ", userId='" + userId + '\'' +
                ", contractor_id='" + contractor_id + '\'' +
                ", signaturePath='" + signaturePath + '\'' +
                '}';
    }
}
