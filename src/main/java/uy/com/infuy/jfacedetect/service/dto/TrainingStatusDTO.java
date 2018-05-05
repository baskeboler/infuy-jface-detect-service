package uy.com.infuy.jfacedetect.service.dto;

import java.io.Serializable;

public class TrainingStatusDTO implements Serializable{

    private String status;
    private String createdDateTime;
    private String lastActionDateTime;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getLastActionDateTime() {
        return lastActionDateTime;
    }

    public void setLastActionDateTime(String lastActionDateTime) {
        this.lastActionDateTime = lastActionDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
