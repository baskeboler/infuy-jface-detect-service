package uy.com.infuy.jfacedetect.web.rest.vm;

import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class ImageUploadVM implements Serializable {
    private String base64EncodedData;
    private Set<FaceDetectionDTO> faces = new HashSet<>();

    public String getBase64EncodedData() {
        return base64EncodedData;
    }

    public void setBase64EncodedData(String base64EncodedData) {
        this.base64EncodedData = base64EncodedData;
    }

    public Set<FaceDetectionDTO> getFaces() {
        return faces;
    }

    public void setFaces(Set<FaceDetectionDTO> faces) {
        this.faces = faces;
    }
}
