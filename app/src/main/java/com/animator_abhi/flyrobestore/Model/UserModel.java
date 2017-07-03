package com.animator_abhi.flyrobestore.Model;

/**
 * Created by ANIMATOR ABHI on 02/07/2017.
 */

public class UserModel {
    private String userId;
    private String mob;
    private String name;
    private String city;
    private String storeId;
    private String emailId;

    public UserModel(String userId, String mob, String name,String emailId, String city, String storeId) {
        this.userId = userId;
        this.mob = mob;
        this.name = name;
        this.city = city;
        this.storeId = storeId;
        this.emailId=emailId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
