package uy.com.infuy.jfacedetect.web.rest;


import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uy.com.infuy.jfacedetect.config.ApplicationProperties;
import uy.com.infuy.jfacedetect.service.FaceDetectionService;
import uy.com.infuy.jfacedetect.service.ImageService;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.persistence.EntityNotFoundException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FaceDetectionResource {

    private final Logger logger = LoggerFactory.getLogger(FaceDetectionResource.class);

    private static final String ENTITY_NAME = "faceDetection";

    private final FaceDetectionService faceDetectionService;
    private final ImageService imageService;
    private final ApplicationProperties properties;

    public FaceDetectionResource(FaceDetectionService faceDetectionService, ImageService imageService, ApplicationProperties properties) {
        this.faceDetectionService = faceDetectionService;
        this.imageService = imageService;
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        logger.info("Initializing Face Detection REST controller");
    }

    @PostMapping("/face-detection")
    @ResponseStatus(HttpStatus.CREATED)
    @Timed
    public ResponseEntity<List<FaceDetectionDTO>> create(@RequestBody List<FaceDetectionDTO> dtos) throws URISyntaxException {
        logger.debug("REST request to create a face detection: {}", dtos);
        if (dtos.stream().anyMatch(d -> d.getId() != null)) {
            throw new BadRequestAlertException("A new face detection cannot already have an ID", ENTITY_NAME, "idexists");
        }

        List<FaceDetectionDTO> save = dtos.stream()
            .map(faceDetectionService::save)
            .collect(Collectors.toList());
        return ResponseEntity.created(new URI("/api/face-detection/"))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME,
                save.stream()
                    .map(d -> d.getId().toString())
                    .reduce("", (a, b) -> a.join(", ", b))))
            .body(save);
    }

    @GetMapping("/face-detection")
    @ResponseStatus(HttpStatus.OK)
    @Timed
    public ResponseEntity<List<FaceDetectionDTO>> getAll(@RequestParam(value = "only-valid", required = false, defaultValue = "NO") String onlyValid) {

        logger.debug("Request to get all face detections");
        if (onlyValid.equals("NO") ) {

            return ResponseEntity.ok(faceDetectionService.getAll());
        } else {
            return ResponseEntity.ok(faceDetectionService.getAllValid());
        }
    }


    @GetMapping("/face-detection/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Timed
    public FaceDetectionDTO get(@PathVariable("id") Long id) {
        return faceDetectionService.get(id).orElseThrow(EntityNotFoundException::new);
    }

    @GetMapping(value = "/files/face-detection/{id}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Timed
    public BufferedImage getFile(@PathVariable("id") Long id) {
        return faceDetectionService.get(id)
            .map(fd -> apply(fd)).orElseThrow(Error::new);
    }

    @Transactional(readOnly = true)
    public BufferedImage apply(FaceDetectionDTO fd) {
        File f = new File(properties.getFaces().getPath(), String.format("%s.jpg", fd.getFaceId()));
        if (f.exists()) {
            try {

                return ImageIO.read(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
