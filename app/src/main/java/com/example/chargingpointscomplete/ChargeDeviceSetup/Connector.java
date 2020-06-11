package com.example.chargingpointscomplete.ChargeDeviceSetup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Connector {

    @SerializedName("ConnectorId")
    @Expose
    private String ConnectorId;
    @SerializedName("ConnectorType")
    @Expose
    private String ConnectorType;
    @SerializedName("RatedOutputkW")
    @Expose
    private String RatedOutputkW;
    @SerializedName("RatedOutputVoltage")
    @Expose
    private String RatedOutputVoltage;
    @SerializedName("RatedOutputCurrent")
    @Expose
    private String RatedOutputCurrent;

    @SerializedName("ChargeMethod")
    @Expose
    private String ChargeMethod;
    @SerializedName("ChargeMode")
    @Expose
    private String ChargeMode;
    @SerializedName("ChargePointStatus")
    @Expose
    private String ChargePointStatus;
    @SerializedName("TetheredCable")
    @Expose
    private String TetheredCable;
    @SerializedName("Information")
    @Expose
    private String Information;
    @SerializedName("Validated")
    @Expose
    private String Validated;

    public String getConnectorId() {
        return ConnectorId;
    }

    public void setConnectorId(String connectorId) {
        ConnectorId = connectorId;
    }

    public String getConnectorType() {
        return ConnectorType;
    }

    public void setConnectorType(String connectorType) {
        ConnectorType = connectorType;
    }

    public String getRatedOutputkW() {
        return RatedOutputkW;
    }

    public void setRatedOutputkW(String ratedOutputkW) {
        RatedOutputkW = ratedOutputkW;
    }

    public String getRatedOutputVoltage() {
        return RatedOutputVoltage;
    }

    public void setRatedOutputVoltage(String ratedOutputVoltage) {
        RatedOutputVoltage = ratedOutputVoltage;
    }

    public String getRatedOutputCurrent() {
        return RatedOutputCurrent;
    }

    public void setRatedOutputCurrent(String ratedOutputCurrent) {
        RatedOutputCurrent = ratedOutputCurrent;
    }

    public String getChargeMethod() {
        return ChargeMethod;
    }

    public void setChargeMethod(String chargeMethod) {
        ChargeMethod = chargeMethod;
    }

    public String getChargeMode() {
        return ChargeMode;
    }

    public void setChargeMode(String chargeMode) {
        ChargeMode = chargeMode;
    }

    public String getChargePointStatus() {
        return ChargePointStatus;
    }

    public void setChargePointStatus(String chargePointStatus) {
        ChargePointStatus = chargePointStatus;
    }

    public String getTetheredCable() {
        return TetheredCable;
    }

    public void setTetheredCable(String tetheredCable) {
        TetheredCable = tetheredCable;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    public String getValidated() {
        return Validated;
    }

    public void setValidated(String validated) {
        Validated = validated;
    }


    @Override
    public String toString() {
        return "Connector{" +
                "ConnectorId='" + ConnectorId + '\'' +
                ", ConnectorType='" + ConnectorType + '\'' +
                ", RatedOutputkW='" + RatedOutputkW + '\'' +
                ", RatedOutputVoltage='" + RatedOutputVoltage + '\'' +
                ", RatedOutputCurrent='" + RatedOutputCurrent + '\'' +
                ", ChargeMethod='" + ChargeMethod + '\'' +
                ", ChargeMode='" + ChargeMode + '\'' +
                ", ChargePointStatus='" + ChargePointStatus + '\'' +
                ", TetheredCable='" + TetheredCable + '\'' +
                ", Information='" + Information + '\'' +
                ", Validated='" + Validated + '\'' +
                '}';
    }
}
