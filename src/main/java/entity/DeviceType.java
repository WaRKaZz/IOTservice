package entity;

import java.util.ArrayList;
import java.util.List;

public class DeviceType {
    private Long deviceTypeID;
    private String deviceTypeName;
    private List<FunctionType> deviceFunctionsList = new ArrayList<>();

    public List<FunctionType> getDeviceFunctionsList() {
        return deviceFunctionsList;
    }

    public void setDeviceFunctionsList(List<FunctionType> deviceFunctionsList) {
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
