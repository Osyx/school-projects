package common;

/**
 * The app specific exception that is used when throwing errors in this application.
 */
public class SystemException extends Exception {
    private final String messageKey;
    private final String message;

    public SystemException(String message) {
        this.message = message;
        this.messageKey = Messages.SYSTEM_ERROR.name();
    }

    public SystemException(String messageKey, String message) {
        this.messageKey = messageKey;
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SystemException: " +
                messageKey + ",\n" +
                "Message: " + message + "\n";
    }
}
