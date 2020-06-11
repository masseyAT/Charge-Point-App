package com.example.chargingpointscomplete.ChargeDeviceSetup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChargeDevice {

    // With serializedName , if the variable in my charge device class is the exact same
    // as a variable in the data set, with the same construction with nested ArrayList etc.
    // It will automatically create an object with the variables i have listed. This is an easy way
    // to leave out any information I don't want to process
    @SerializedName("ChargeDeviceName")
    @Expose
    private String ChargeDeviceName;

    @SerializedName("Connector")
    @Expose
    private ArrayList<Connector> Connector = new ArrayList<>();

    @SerializedName("ChargeDeviceLocation")
    @Expose
    private ChargeDeviceLocation ChargeDeviceLocation;

    @SerializedName("DeviceOwner")
    @Expose
    private DeviceOwner deviceOwner;

    @SerializedName("LocationType")
    @Expose
    private String LocationType;

    @SerializedName("Accessible24Hours")
    @Expose
    private String Accessible24Hours;

    @SerializedName("ChargeDeviceStatus")
    @Expose
    private String ChargeDeviceStatus;

    @SerializedName("PaymentRequiredFlag")
    @Expose
    private String PaymentRequired;

    @SerializedName("PaymentDetails")
    @Expose
    private String PaymentDetails;

    @SerializedName("SubscriptionRequiredFlag")
    @Expose
    private String SubscriptionRequired;

    @SerializedName("SubscriptionDetails")
    @Expose
    private String SubscriptionDetails;

    @SerializedName("ParkingFeesFlag")
    @Expose
    private String ParkingFees;

    @SerializedName("ParkingFeesDetails")
    @Expose
    private String ParkingFeesDetails;

    @SerializedName("ParkingFeesUrl")
    @Expose
    private String ParkingFeesUrl;


    public ChargeDevice() {
    }

    public ChargeDevice(String chargeDeviceName, ArrayList<Connector> connector) {

        this.ChargeDeviceName = chargeDeviceName;
        this.Connector.addAll(connector);

    }

    public String getChargeDeviceName() {
        return ChargeDeviceName;
    }

    public void setChargeDeviceName(String chargeDeviceName) {
        ChargeDeviceName = chargeDeviceName;
    }

    public ArrayList<com.example.chargingpointscomplete.ChargeDeviceSetup.Connector> getConnector() {
        return Connector;
    }

    public void setConnector(ArrayList<com.example.chargingpointscomplete.ChargeDeviceSetup.Connector> connector) {
        Connector = connector;
    }

    public com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceLocation getChargeDeviceLocation() {
        return ChargeDeviceLocation;
    }

    public void setChargeDeviceLocation(com.example.chargingpointscomplete.ChargeDeviceSetup.ChargeDeviceLocation chargeDeviceLocation) {
        ChargeDeviceLocation = chargeDeviceLocation;
    }

    public DeviceOwner getDeviceOwner() {
        return deviceOwner;
    }

    public void setDeviceOwner(DeviceOwner deviceOwner) {
        this.deviceOwner = deviceOwner;
    }

    public String getLocationType() {
        return LocationType;
    }

    public void setLocationType(String locationType) {
        LocationType = locationType;
    }

    public String getAccessible24Hours() {
        return Accessible24Hours;
    }

    public void setAccessible24Hours(String accessible24Hours) {
        Accessible24Hours = accessible24Hours;
    }

    public String getChargeDeviceStatus() {
        return ChargeDeviceStatus;
    }

    public void setChargeDeviceStatus(String chargeDeviceStatus) {
        ChargeDeviceStatus = chargeDeviceStatus;
    }

    public String getPaymentRequired() {
        return PaymentRequired;
    }

    public void setPaymentRequired(String paymentRequired) {
        PaymentRequired = paymentRequired;
    }

    public String getPaymentDetails() {
        return PaymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        PaymentDetails = paymentDetails;
    }

    public String getSubscriptionRequired() {
        return SubscriptionRequired;
    }

    public void setSubscriptionRequired(String subscriptionRequired) {
        SubscriptionRequired = subscriptionRequired;
    }

    public String getSubscriptionDetails() {
        return SubscriptionDetails;
    }

    public void setSubscriptionDetails(String subscriptionDetails) {
        SubscriptionDetails = subscriptionDetails;
    }

    public String getParkingFees() {
        return ParkingFees;
    }

    public void setParkingFees(String parkingFees) {
        ParkingFees = parkingFees;
    }

    public String getParkingFeesDetails() {
        return ParkingFeesDetails;
    }

    public void setParkingFeesDetails(String parkingFeesDetails) {
        ParkingFeesDetails = parkingFeesDetails;
    }

    public String getParkingFeesUrl() {
        return ParkingFeesUrl;
    }

    public void setParkingFeesUrl(String parkingFeesUrl) {
        ParkingFeesUrl = parkingFeesUrl;
    }

    @Override
    public String toString() {
        return "ChargeDevice{" +
                "ChargeDeviceName='" + ChargeDeviceName + '\'' +
                ", Connector=" + Connector +
                ", ChargeDeviceLocation=" + ChargeDeviceLocation +
                ", deviceOwner=" + deviceOwner +
                ", LocationType='" + LocationType + '\'' +
                ", Accessible24Hours='" + Accessible24Hours + '\'' +
                ", ChargeDeviceStatus='" + ChargeDeviceStatus + '\'' +
                ", PaymentRequired='" + PaymentRequired + '\'' +
                ", PaymentDetails='" + PaymentDetails + '\'' +
                ", SubscriptionRequired='" + SubscriptionRequired + '\'' +
                ", SubscriptionDetails='" + SubscriptionDetails + '\'' +
                ", ParkingFees='" + ParkingFees + '\'' +
                ", ParkingFeesDetails='" + ParkingFeesDetails + '\'' +
                ", ParkingFeesUrl='" + ParkingFeesUrl + '\'' +
                '}';
    }
}


