package uy.com.infuy.jfacedetect.service.errors;

public class AzureNotFoundException extends AzureFaceApiException{
    public AzureNotFoundException() {
    }

    public AzureNotFoundException(String message) {
        super(message);
    }

    public AzureNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AzureNotFoundException(Throwable cause) {
        super(cause);
    }

    public AzureNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
