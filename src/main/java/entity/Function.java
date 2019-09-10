package entity;


public class Function {
    private boolean isFunctionInput;
    private String functionName;
    private boolean functionTrue;
    private int functionInteger;
    private String functionText;
    private Long functionId;

    public boolean isFunctionInput() {
        return isFunctionInput;
    }

    public void setFunctionInput(boolean functionInput) {
        isFunctionInput = functionInput;
    }

    public void setFunctionTrue(boolean functionBoolean) {
        this.functionTrue = functionBoolean;
    }

    public int getFunctionInteger() {
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
