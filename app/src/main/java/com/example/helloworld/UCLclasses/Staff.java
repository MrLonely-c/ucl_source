package com.example.helloworld.UCLclasses;

public class Staff {
    private String staffID;
    private String staffName;
    private String IDCardNo;
    private String contactNo;

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getIDCardNo() {
        return IDCardNo;
    }

    public void setIDCardNo(String IDCardNo) {
        this.IDCardNo = IDCardNo;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Staff(String staffID, String staffName, String IDCardNo, String contactNo) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.IDCardNo = IDCardNo;
        this.contactNo = contactNo;
    }
}
