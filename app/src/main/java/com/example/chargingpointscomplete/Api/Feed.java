package com.example.chargingpointscomplete.Api;

import com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDevice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Feed {
    @SerializedName("ChargeDevice")
    @Expose
    private ArrayList<com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDevice> ChargeDevice;

    public ArrayList<ChargeDevice> getChargeDevice() {
        return ChargeDevice;
    }

    public void setChargeDevice(ArrayList<ChargeDevice> chargeDevice) {
        ChargeDevice = chargeDevice;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "ChargeDevice=" + ChargeDevice +
                '}';
    }
}