package kz.epam.IOTservice.entity;

import java.util.ArrayList;
import java.util.List;

public class DeviceType {
    private Long deviceTypeID;
    private String deviceTypeName;
    private List<FunctionDefinition> deviceFunctionsList = new ArrayList<>();

    public List<FunctionDefinition> getDeviceFunctionsList() {
        return deviceFunctionsList;
    }

    public void setDeviceFunctionsList(List<FunctionDefinition> deviceFunctionsList) {
        this.deviceFunctionsList = deviceFunctionsList;
    }

    public Long getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(Long deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }

    public String getDeviceTypeName() {
        return deviceTypeName;
    }

    public void setDeviceTypeName(String deviceTypeName) {
        this.deviceTypeName = deviceTypeName;
    }
}
