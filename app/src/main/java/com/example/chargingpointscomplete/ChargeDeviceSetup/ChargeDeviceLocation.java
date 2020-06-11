package com.example.chargingpointscomplete.ChargeDeviceSetup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChargeDeviceLocation {

    @SerializedName("Latitude")
    @Expose
    private String Latitude;
    @SerializedName("Longitude")
    @Expose
    private String Longitude;

    @SerializedName("Address")
    @Expose
    private Address Address;

    public com.example.chargingpointscomplete.ChargeDeviceSetup.Address getAddress() {
        return Address;
    }

    public void setAddress(com.example.chargingpointscomplete.ChargeDeviceSetup.Address address) {
        Address = address;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "ChargeDeviceLocation{" +
                "Latitude='" + Latitude + '\'' +
                ", Longitude='" + Longitude + '\'' +
                ", Address=" + Address +
                '}';
    }
}
