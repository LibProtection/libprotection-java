package main.java;

public class FormatResult{

    public enum FormatStatus{
        Success,
        AttackDetected,
        Exception,
    }

    public FormatResult(String operationResult, String formatResult, FormatStatus formatStatus){
        this.operationResult = operationResult;
        this.formatResult = formatResult;
        this.formatStatus = formatStatus;
    }

    private final String operationResult;
    private final String formatResult;
    private final FormatStatus formatStatus;

    public String getFormatResult() {
        return formatResult;
    }

    public String getOperationResult() {
        return operationResult;
    }

    public FormatStatus getFormatStatus() {
        return formatStatus;
    }
}