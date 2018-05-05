package uy.com.infuy.jfacedetect.service.impl;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uy.com.infuy.jfacedetect.config.ApplicationProperties;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.domain.Rectangle;
import uy.com.infuy.jfacedetect.repository.ImageRepository;
import uy.com.infuy.jfacedetect.service.ImageProcessingService;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Timed
@Transactional
public class ImageProcessingServiceImpl implements ImageProcessingService {

    private final Logger log = LoggerFactory.getLogger(ImageProcessingServiceImpl.class);
    private final ApplicationProperties properties;
    //    private ImageService imageService;
//    private FaceDetectionService faceDetectionService;
    private ImageRepository imageRepository;

    public ImageProcessingServiceImpl(ApplicationProperties properties, ImageRepository imageRepository) {
        this.properties = properties;
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void init() {
        log.info("Initializing Image Processing Service");
        ensureFacesDirectory();
        log.info("Processing uploads");
        try {
            processUploadsDirectory();
        } catch (IOException e) {
            log.error("Error: ", e);
            e.printStackTrace();
        }
        log.info("Finished processing uploads");
    }

    @Override
    public void ensureFacesDirectory() {
        log.info("Making sure faces directory exists");
        File uploadsDir = new File(properties.getFaces().getPath());
        if (!uploadsDir.exists()) {
            if (!uploadsDir.mkdirs()) {
                throw new RuntimeException("Failed to create the faces directory");
            }
        }
        if (Files.isDirectory(uploadsDir.toPath())) {
        } else {
            throw new RuntimeException("Failed to create faces directory");
        }
    }

    @Override
    @Transactional
    public void processUploadsDirectory() throws IOException {
        String uploadsPath = properties.getUploads().getPath();
        Files.list(new File(uploadsPath).toPath())
            .filter(p -> p.toString().endsWith("__processed.jpg"))
            .peek(p -> log.debug("Processing {}", p.toString()))
            .map(Path::getFileName)
            .map(Path::toString)
            .map(s -> s.replace("__processed.jpg", ""))
            .peek(s -> log.info("Looking for {} in DB", s))
            .map(imageRepository::findImageIdByFilenameUuid)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .peek(i -> log.info("Processing image id {}", i))
            .forEach(imageId -> {
                imageRepository.findOneById(imageId)
                    .ifPresent(image -> {
                        String imagePath = image.getPath();
                        image.getFaces().stream()
                            .forEach(face -> {
                                log.info("Extracting Face region from image {}", imageId);
                                extractFaceRegion(imagePath, face);
                            });
                        String toRemove = imagePath.replace(".jpg", "__processed.jpg");
                        try {
                            File destination = new File(
                                toRemove
                                    .replace(
                                        "__processed.jpg",
                                        "__crops_generated.jpg"
                                    )
                            );
                            Files.copy(
                                new File(toRemove).toPath(),
                                destination.toPath(),
                                StandardCopyOption.REPLACE_EXISTING
                            );
                            Files.delete(new File(toRemove).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            });
    }

    private void extractFaceRegion(String imagePath, FaceDetection fd) {

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Rectangle rectangle = fd.getFaceRectangle();
            if (rectangle.getHeight() == 0 || rectangle.getHeight() == 0) {
                log.warn("Invalid rectangle for Face Detection {}", fd.toString());
                return;
            }

            BufferedImage subimage = image.getSubimage(rectangle.getLeft(), rectangle.getTop(), rectangle.getWidth(), rectangle.getHeight());

            try {
                String newFilePath = String.format("%s/%s.jpg", properties.getFaces().getPath(), fd.getFaceId());
                log.info("Preparing to save {}", newFilePath);
                ImageIO.write(subimage, "jpg", new File(newFilePath));
                log.info("filed {} saved successfully", newFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
