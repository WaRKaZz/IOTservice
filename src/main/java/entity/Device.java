package entity;

import java.util.ArrayList;
import java.util.List;

public class Device {
    private List<Function> functions = new ArrayList<>();
    private Long deviceID;
    private String deviceName;
    private String deviceDefinitionName;
    private Long deviceHomePlacedID;
    private Long deviceDefinitionID;

    public Long getDeviceHomePlacedID() {
        return deviceHomePlacedID;
    }

    public void setDeviceHomePlacedID(Long deviceHomePlacedID) {
        this.deviceHomePlacedID = deviceHomePlacedID;
    }

    public Long getDeviceDefinitionID() {
        return deviceDefinitionID;
    }

    public void setDeviceDefinitionID(Long deviceDefinitionID) {
        this.deviceDefinitionID = deviceDefinitionID;
    }

    public String getDeviceDefinitionName() {
        return deviceDefinitionName;
    }

    public void setDeviceDefinitionName(String deviceDefinitionName) {
        this.deviceDefinitionName = deviceDefinitionName;
    }

    public Long getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(Long deviceID) {
        this.deviceID = deviceID;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Function> functions) {
        this.functions = functions;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
