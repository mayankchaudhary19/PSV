package com.example.myapplication.Models;

public class AddressModel {
    private String fullName;
    private String contactNumber;
    private String addressLine1;
    private String addressLine2;
//    private String additionalInfo;
    private String state;
    private String pincode;
    private String  addressType;
    private boolean selectedAddress;

    public AddressModel(String fullName, String contactNumber, String addressLine1, String addressLine2, String state, String pincode, String addressType, boolean selectedAddress) {
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.state = state;
        this.pincode = pincode;
        this.addressType=addressType;
        this.selectedAddress = selectedAddress;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public boolean isSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(boolean selectedAddress) {
        this.selectedAddress = selectedAddress;
    }
}
