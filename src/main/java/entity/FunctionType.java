package entity;

public class FunctionType {
    private Long functionTypeID;
    private String functionName;
    private boolean isInput;
    private Long deviceTypeID;

    public Long getDeviceTypeID() {
        return deviceTypeID;
    }

    public void setDeviceTypeID(Long deviceTypeID) {
        this.deviceTypeID = deviceTypeID;
    }

    public Long getFunctionTypeID() {
        return functionTypeID;
    }

    public void setFunctionTypeID(Long functionTypeID) {
        this.functionTypeID = functionTypeID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public boolean isInput() {
        return isInput;
    }

    public void setInput(boolean input) {
        isInput = input;
    }
}
