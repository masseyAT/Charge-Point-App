package com.example.chargingpointscomplete.ChargeDeviceSetup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("SubBuildingName")
    @Expose
    private String SubBuildingName;
    @SerializedName("BuildingName")
    @Expose
    private String BuildingName;
    @SerializedName("BuildingNumber")
    @Expose
    private String BuildingNumber;
    @SerializedName("Thoroughfare")
    @Expose
    private String Thoroughfare;
    @SerializedName("Street")
    @Expose
    private String Street;
    @SerializedName("DoubleDependantLocality")
    @Expose
    private String DoubleDependantLocality;
    @SerializedName("DependantLocality")
    @Expose
    private String DependantLocality;
    @SerializedName("PostTown")
    @Expose
    private String PostTown;
    @SerializedName("County")
    @Expose
    private String County;
    @SerializedName("PostCode")
    @Expose
    private String PostCode;
    @SerializedName("Country")
    @Expose
    private String Country;
    @SerializedName("UPRN")
    @Expose
    private String UPRN;

    public String getSubBuildingName() {
        return SubBuildingName;
    }

    public void setSubBuildingName(String subBuildingName) {
        SubBuildingName = subBuildingName;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getBuildingNumber() {
        return BuildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        BuildingNumber = buildingNumber;
    }

    public String getThoroughfare() {
        return Thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        Thoroughfare = thoroughfare;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getDoubleDependantLocality() {
        return DoubleDependantLocality;
    }

    public void setDoubleDependantLocality(String doubleDependantLocality) {
        DoubleDependantLocality = doubleDependantLocality;
    }

    public String getDependantLocality() {
        return DependantLocality;
    }

    public void setDependantLocality(String dependantLocality) {
        DependantLocality = dependantLocality;
    }

    public String getPostTown() {
        return PostTown;
    }

    public void setPostTown(String postTown) {
        PostTown = postTown;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getUPRN() {
        return UPRN;
    }

    public void setUPRN(String UPRN) {
        this.UPRN = UPRN;
    }


    @Override
    public String toString() {
        return "Address{" +
                "SubBuildingName='" + SubBuildingName + '\'' +
                ", BuildingName='" + BuildingName + '\'' +
                ", BuildingNumber='" + BuildingNumber + '\'' +
                ", Thoroughfare='" + Thoroughfare + '\'' +
                ", Street='" + Street + '\'' +
                ", DoubleDependantLocality='" + DoubleDependantLocality + '\'' +
                ", DependantLocality='" + DependantLocality + '\'' +
                ", PostTown='" + PostTown + '\'' +
                ", County='" + County + '\'' +
                ", PostCode='" + PostCode + '\'' +
                ", Country='" + Country + '\'' +
                ", UPRN='" + UPRN + '\'' +
                '}';
    }
}
