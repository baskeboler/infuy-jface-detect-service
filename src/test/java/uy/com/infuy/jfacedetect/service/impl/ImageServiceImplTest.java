package uy.com.infuy.jfacedetect.service.impl;

import org.junit.Test;
import uy.com.infuy.jfacedetect.service.ImageService;

import java.io.File;
import java.io.IOException;

public class ImageServiceImplTest {
    ImageService service = new ImageServiceImpl(null, faceDetectionRepository, null, faceDetectionMapper);

    @Test
    public void processImage() throws IOException {
        File f = new File("/home/victor/dev/jface-detect/uploads/24162.jpg");
        service.processImage(f);

    }
}
