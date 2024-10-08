package com.revshop.model;

public class Address {
    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String doorNo;
    private String buildingName;
    private String address;
    private String landmark;
    private String city;
    private String district;
    private String pincode;

    // Constructors
    public Address() {
    }

    public Address(int userId, String firstName, String lastName, String doorNo, String buildingName, String address, String landmark, String city, String district, String pincode) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.doorNo = doorNo;
        this.buildingName = buildingName;
        this.address = address;
        this.landmark = landmark;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }

    public Address(int id, int userId, String firstName, String lastName, String doorNo, String buildingName, String address, String landmark, String city, String district, String pincode) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.doorNo = doorNo;
        this.buildingName = buildingName;
        this.address = address;
        this.landmark = landmark;
        this.city = city;
        this.district = district;
        this.pincode = pincode;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", " +
               doorNo + ", " + buildingName + ", " +
               address + ", " + landmark + ", " +
               city + ", " + district + ", " + pincode;
    }
}
