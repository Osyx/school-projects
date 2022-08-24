package common;

public class FileError extends Exception {
    private final String errorMsg;
    private final Exception exception;

    public FileError(String errorMsg) {
        this.errorMsg = errorMsg;
        this.exception = null;
    }

    public FileError(String errorMsg, Exception exception) {
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
