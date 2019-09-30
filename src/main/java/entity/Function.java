package entity;


public class Function {
    private Boolean functionInput;
    private String functionName;
    private Boolean functionTrue = false;
    private Integer functionInteger = 0;
    private String functionText = "";
    private Long functionId;
    private String functionType;

    public String getFunctionType() {
        return functionType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public Boolean getFunctionInput() {
        return functionInput;
    }

    public void setFunctionInput(boolean functionInput) {
        this.functionInput = functionInput;
    }

    public Boolean getFunctionTrue() {
        return functionTrue;
    }

    public void setFunctionTrue(boolean functionBoolean) {
        this.functionTrue = functionBoolean;
    }

    public Integer getFunctionInteger() {
        return functionInteger;
    }

    public void setFunctionInteger(int functionInteger) {
        this.functionInteger = functionInteger;
    }

    public String getFunctionText() {
        return functionText;
    }

    public void setFunctionText(String functionText) {
        this.functionText = functionText;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
