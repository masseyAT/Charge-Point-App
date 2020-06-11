package com.example.chargingpointscomplete.ChargeDeviceSetup;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

// Final charge device object with all the variables I needed. I went this way about creating
// an object to remove all the nested variables and lower the amount of information being processed greatly
@Entity(tableName = "charge_table")
public class ChargeDeviceComplete {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "charge_device_name")
    private String ChargeDeviceName;
    @ColumnInfo(name = "location_type")
    private String LocationType;
    @ColumnInfo(name = "accessible_24_hours")
    private String Accessible24Hours;
    @ColumnInfo(name = "charge_device_status")
    private String ChargeDeviceStatus;
    @ColumnInfo(name = "payment_required_flag")
    private String PaymentRequired;
    @ColumnInfo(name = "payment_details")
    private String PaymentDetails;
    @ColumnInfo(name = "subscription_required_flag")
    private String SubscriptionRequired;
    @ColumnInfo(name = "subscription_details")
    private String SubscriptionDetails;
    @ColumnInfo(name = "parking_fees_flag")
    private String ParkingFees;
    @ColumnInfo(name = "parking_fees_details")
    private String ParkingFeesDetails;
    @ColumnInfo(name = "parking_fees_url")
    private String ParkingFeesUrl;
    @ColumnInfo(name = "connector")
    private ArrayList<Connector> Connector = new ArrayList<>();
    @ColumnInfo(name = "device_owner_address")
    private String DeviceOwnerAddress;
    @ColumnInfo(name = "device_owner_website")
    private String DeviceOwnerWebsite;
    @ColumnInfo(name = "device_owner_telephone_no")
    private String DeviceOwnerTelephoneNo;
    @ColumnInfo(name = "user_lat")
    private double UserLatitude;
    @ColumnInfo(name = "lat")
    private String Latitude;
    @ColumnInfo(name = "user_long")
    private double UserLongitude;
    @ColumnInfo(name = "long")
    private String Longitude;
    @ColumnInfo(name = "post_code")
    private String PostCode;
    @ColumnInfo(name = "building_name")
    private String BuildingName;
    @ColumnInfo(name = "throughfare")
    private String Thoroughfare;
    @ColumnInfo(name = "post_town")
    private String PostTown;
    @ColumnInfo(name = "county")
    private String County;


    public ChargeDeviceComplete(String chargeDeviceName, String locationType, String accessible24Hours,
                          String chargeDeviceStatus, String paymentRequired, String paymentDetails,
                          String subscriptionRequired, String subscriptionDetails, String parkingFees,
                          String parkingFeesDetails, String parkingFeesUrl, ArrayList<Connector> connector,
                          String deviceOwnerAddress, String deviceOwnerWebsite, String deviceOwnerTelephoneNo,
                          double userLatitude, String latitude, double userLongitude, String longitude, String postcode,
                          String buildingName, String thoroughfare, String postTown, String county) {
        ChargeDeviceName = chargeDeviceName;
        LocationType = locationType;
        Accessible24Hours = accessible24Hours;
        ChargeDeviceStatus = chargeDeviceStatus;
        PaymentRequired = paymentRequired;
        PaymentDetails = paymentDetails;
        SubscriptionRequired = subscriptionRequired;
        SubscriptionDetails = subscriptionDetails;
        ParkingFees = parkingFees;
        ParkingFeesDetails = parkingFeesDetails;
        ParkingFeesUrl = parkingFeesUrl;
        Connector = connector;
        DeviceOwnerAddress = deviceOwnerAddress;
        DeviceOwnerWebsite = deviceOwnerWebsite;
        DeviceOwnerTelephoneNo = deviceOwnerTelephoneNo;
        UserLatitude = userLatitude;
        Latitude = latitude;
        UserLongitude = userLongitude;
        Longitude = longitude;
        PostCode = postcode;
        BuildingName = buildingName;
        Thoroughfare = thoroughfare;
        PostTown = postTown;
        County = county;
    }

    public ChargeDeviceComplete(double userLatitude, double userLongitude){
        UserLatitude = userLatitude;
        UserLongitude = userLongitude;
    }

    public ChargeDeviceComplete() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Type converters has been used as I had trouble inserting a nested array into the database
    // This was a shortcut method rather than creating two tables and merging them together after
    // This method creates a json string and then back to object automatically on database exit.
    // I was able to do this as my api was able to search by connector and I didn't need to do a
    // select query on it
    @TypeConverters(Converters.class)
    public ArrayList<Connector> getConnector() {
        return Connector;
    }

    public void setConnector(ArrayList<Connector> connector) {Connector = connector;}

    public String getChargeDeviceName() {
        return ChargeDeviceName;
    }

    public void setChargeDeviceName(String chargeDeviceName) {
        ChargeDeviceName = chargeDeviceName;
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

    public String getDeviceOwnerAddress() {
        return DeviceOwnerAddress;
    }

    public void setDeviceOwnerAddress(String deviceOwnerAddress) {
        DeviceOwnerAddress = deviceOwnerAddress;
    }

    public String getDeviceOwnerWebsite() {
        return DeviceOwnerWebsite;
    }

    public void setDeviceOwnerWebsite(String deviceOwnerWebsite) {
        DeviceOwnerWebsite = deviceOwnerWebsite;
    }

    public String getDeviceOwnerTelephoneNo() {
        return DeviceOwnerTelephoneNo;
    }

    public void setDeviceOwnerTelephoneNo(String deviceOwnerTelephoneNo) {
        DeviceOwnerTelephoneNo = deviceOwnerTelephoneNo;
    }

    public double getUserLatitude() {
        return UserLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        UserLatitude = userLatitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public double getUserLongitude() {
        return UserLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        UserLongitude = userLongitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getThoroughfare() {
        return Thoroughfare;
    }

    public void setThoroughfare(String thoroughfare) {
        Thoroughfare = thoroughfare;
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

    @Override
    public String toString() {
        return "ChargeDeviceDB{" +
                "id=" + id +
                ", ChargeDeviceName='" + ChargeDeviceName + '\'' +
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
                ", Connector=" + Connector +
                ", DeviceOwnerAddress='" + DeviceOwnerAddress + '\'' +
                ", DeviceOwnerWebsite='" + DeviceOwnerWebsite + '\'' +
                ", DeviceOwnerTelephoneNo='" + DeviceOwnerTelephoneNo + '\'' +
                ", UserLatitude=" + UserLatitude +
                ", Latitude='" + Latitude + '\'' +
                ", UserLongitude=" + UserLongitude +
                ", Longitude='" + Longitude + '\'' +
                ", PostCode='" + PostCode + '\'' +
                ", BuildingName='" + BuildingName + '\'' +
                ", Thoroughfare='" + Thoroughfare + '\'' +
                ", PostTown='" + PostTown + '\'' +
                ", County='" + County + '\'' +
                '}';
    }
}

