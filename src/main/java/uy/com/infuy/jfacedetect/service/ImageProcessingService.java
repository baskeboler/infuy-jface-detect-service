package uy.com.infuy.jfacedetect.service;

import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface ImageProcessingService {

    void ensureFacesDirectory();

    @Transactional
    void processUploadsDirectory() throws IOException;
}
