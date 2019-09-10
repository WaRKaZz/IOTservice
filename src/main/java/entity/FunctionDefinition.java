package entity;

public class FunctionDefinition {
    private Long functionDefinitionID;
    private String functionName;
    private boolean isInput;
    private Long deviceDefinitionID;

    public Long getDeviceDefinitionID() {
        return deviceDefinitionID;
    }

    public void setDeviceDefinitionID(Long deviceDefinitionID) {
        this.deviceDefinitionID = deviceDefinitionID;
    }

    public Long getFunctionDefinitionID() {
        return functionDefinitionID;
    }

    public void setFunctionDefinitionID(Long functionDefinitionID) {
        this.functionDefinitionID = functionDefinitionID;
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
