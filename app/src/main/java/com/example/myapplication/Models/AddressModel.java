package com.example.myapplication.Models;

public class AddressModel {
    private String fullName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String state;
    private String pinCode;
    private boolean selectedAddress;

    public AddressModel(String fullName, String phoneNumber, String addressLine1, String addressLine2, String state, String pinCode,Boolean selectedAddress) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.state = state;
        this.pinCode = pinCode;
        this.selectedAddress=selectedAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public boolean getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(boolean selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
}
