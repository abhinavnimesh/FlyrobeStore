package com.animator_abhi.flyrobestore.Model;

/**
 * Created by ANIMATOR ABHI on 04/07/2017.
 */

public class TransactionModel {
   private String  creditAmount, phoneNo,customerName, receipt, email, notes, deviceId,userId;
    private String rrNo,authCode;
    private double timestamp;

    public TransactionModel(String creditAmount, String customerName,String phoneNo,  String email,String receipt, String notes,  String userId,String rrNo,String authCode,double timestamp) {
        this.creditAmount = creditAmount;
        this.phoneNo = phoneNo;
        this.customerName = customerName;
        this.receipt = receipt;
        this.email = email;
        this.notes = notes;
        //this.deviceId = deviceId;
        this.userId = userId;
        this.rrNo=rrNo;
        this.authCode=authCode;
        this.timestamp=timestamp;
    }


    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }


    public String getRrNo() {
        return rrNo;
    }

    public void setRrNo(String rrNo) {
        this.rrNo = rrNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
