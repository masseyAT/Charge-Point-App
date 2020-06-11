package com.example.chargingpointscomplete.ChargeDeviceSetup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceOwner {

    @SerializedName("OrganisationName")
    @Expose
    private String OrganisationName;

    @SerializedName("Website")
    @Expose
    private String Website;

    @SerializedName("TelephoneNo")
    @Expose
    private String TelephoneNo;

    public String getOrganisationName() {
        return OrganisationName;
    }

    public void setOrganisationName(String organisationName) {
        OrganisationName = organisationName;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getTelephoneNo() {
        return TelephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        TelephoneNo = telephoneNo;
    }

    @Override
    public String toString() {
        return "DeviceOwner{" +
                "OrganisationName='" + OrganisationName + '\'' +
                ", Website='" + Website + '\'' +
                ", TelephoneNo='" + TelephoneNo + '\'' +
                '}';
    }
}
