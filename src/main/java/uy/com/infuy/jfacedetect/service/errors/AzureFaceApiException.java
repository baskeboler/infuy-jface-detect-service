package uy.com.infuy.jfacedetect.service.errors;

public class AzureFaceApiException extends RuntimeException{
    public AzureFaceApiException() {
    }

    public AzureFaceApiException(String message) {
        super(message);
    }

    public AzureFaceApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureFaceApiException(Throwable cause) {
        super(cause);
    }

    public AzureFaceApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
