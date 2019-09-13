package entity;

import java.util.ArrayList;
import java.util.List;

public class DeviceDefinition {
    private Long deviceDefinitionID;
    private String deviceName;
    private List<FunctionDefinition> deviceFunctionsList = new ArrayList<>();

    public List<FunctionDefinition> getDeviceFunctionsList() {
        return deviceFunctionsList;
    }

    public void setDeviceFunctionsList(List<FunctionDefinition> deviceFunctionsList) {
        this.deviceFunctionsList = deviceFunctionsList;
    }

    public Long getDeviceDefinitionID() {
        return deviceDefinitionID;
    }

    public void setDeviceDefinitionID(Long deviceDefinitionID) {
        this.deviceDefinitionID = deviceDefinitionID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
