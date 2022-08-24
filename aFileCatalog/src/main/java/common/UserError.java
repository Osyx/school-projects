package common;

public class UserError extends Exception {
    private final String errorMsg;
    private final Exception exception;

    public UserError(String errorMsg) {
        this.errorMsg = errorMsg;
        this.exception = null;
    }

    public UserError(String errorMsg, Exception exception) {
        this.errorMsg = errorMsg;
        this.exception = exception;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Exception getException() {
        return exception;
    }
}