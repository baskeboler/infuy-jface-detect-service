package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ggsoft.Annotator;
import com.ggsoft.Builders;
import com.ggsoft.annotations.Annotation;
import com.ggsoft.annotations.LabelAnnotation;
import com.ggsoft.annotations.RectangleAnnotation;
import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.web.multipart.MultipartFile;
import uy.com.infuy.jfacedetect.config.ApplicationProperties;
import uy.com.infuy.jfacedetect.service.FaceDetectionService;
import uy.com.infuy.jfacedetect.service.ImageService;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.errors.InternalServerErrorException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import uy.com.infuy.jfacedetect.web.rest.util.PaginationUtil;
import uy.com.infuy.jfacedetect.service.dto.ImageDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uy.com.infuy.jfacedetect.web.rest.vm.ImageUploadVM;

import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    private final ImageService imageService;
    private final ApplicationProperties properties;
    private final FaceDetectionService faceDetectionService;

    public ImageResource(ImageService imageService, ApplicationProperties properties, FaceDetectionService faceDetectionService) {
        this.imageService = imageService;
        this.properties = properties;
        this.faceDetectionService = faceDetectionService;
    }

    /**
     * POST  /images : Create a new image.
     *
     * @param imageDTO the imageDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imageDTO, or with status 400 (Bad Request) if the image has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
        log.debug("REST request to save Image : {}", imageDTO);
        if (imageDTO.getId() != null) {
            throw new BadRequestAlertException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /images : Updates an existing image.
     *
     * @param imageDTO the imageDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imageDTO,
     * or with status 400 (Bad Request) if the imageDTO is not valid,
     * or with status 500 (Internal Server Error) if the imageDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO) throws URISyntaxException {
        log.debug("REST request to update Image : {}", imageDTO);
        if (imageDTO.getId() == null) {
            return createImage(imageDTO);
        }
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imageDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /images : get all the images.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of images in body
     */
    @GetMapping("/images")
    @Timed
    public ResponseEntity<List<ImageDTO>> getAllImages(Pageable pageable) {
        log.debug("REST request to get a page of Images");
        Page<ImageDTO> page = imageService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/images");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /images/:id : get the "id" image.
     *
     * @param id the id of the imageDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imageDTO, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}")
    @Timed
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
        log.debug("REST request to get Image : {}", id);
        ImageDTO imageDTO = imageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(imageDTO));
    }

    @GetMapping("/images/{id}/faces")
    @Timed
    public ResponseEntity<List<FaceDetectionDTO>> getFaceDetections(@PathVariable Long id) {
        List<FaceDetectionDTO> facesDetected = imageService.getFacesDetected(id);
        return ResponseEntity.ok(facesDetected);
    }

    @GetMapping(value = "/files/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Timed
    public ResponseEntity<BufferedImage> getImageFile(@PathVariable Long id) {
        log.debug("REST request to get Image file: {}", id);
        return ResponseUtil.wrapOrNotFound(imageService.getImageFile(id));
    }

    /**
     * DELETE  /images/:id : delete the "id" image.
     *
     * @param id the id of the imageDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/images/{id}")
    @Timed
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);
        imageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping(value = "/upload/image", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseStatus(HttpStatus.OK)
    @Timed
    public BufferedImage uploadImage(
        @RequestBody ImageUploadVM upload
    ) throws URISyntaxException, IOException {

        UUID uuid = UUID.randomUUID();

        File uploadsDirectory = new File(properties.getUploads().getPath());
        if (!uploadsDirectory.exists()) {
            uploadsDirectory.mkdir();
        }
        if (!uploadsDirectory.isDirectory()) {
            throw new InternalServerErrorException("Uploads directory not available");
        }
        File f = new File(uploadsDirectory, uuid.toString() + ".jpg");
        try {
            String data = upload.getBase64EncodedData();
            byte[] bytes = Base64.getDecoder().decode(data);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            IOUtils.copy(inputStream, new FileOutputStream(f));
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }


        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setPath(f.getPath());
        imageDTO = imageService.save(imageDTO);
        final Long imageId = imageDTO.getId();
        Set<FaceDetectionDTO> faces = upload.getFaces();

        Set<FaceDetectionDTO> saved = faces.stream()
            .map(faceDetectionService::save)
            .collect(Collectors.toSet());

        saved.stream()
            .forEach(detection -> imageService.addFaceDetection(imageId, detection.getId()));

        Annotator annotator = new Annotator();
        List<Annotation> annotations = new ArrayList<>();
        Font font = new Font("SansSerif", Font.BOLD, 18);
        saved.stream()
            .forEach(a -> {
                FaceDetectionDTO.FaceRectangle rectangle = a.getFaceRectangle();
                RectangleAnnotation rect = Builders.rect().withTop(rectangle.getTop())
                    .withLeft(rectangle.getLeft())
                    .withWidth(rectangle.getWidth())
                    .withHeight(rectangle.getHeight())
                    .build();
                annotations.add(rect);
                LabelAnnotation lbl = Builders.label().withX(rect.getLeft())
                    .withY(rect.getTop() - 10)
                    .withText(a.getFaceId())
                    .withFont(font)
                    .build();
                annotations.add(lbl);
            });
        String annotated = uploadsDirectory + "/" + uuid.toString() + "__processed.jpg";
        annotator.drawAnnotations(f.getAbsolutePath(),
            uploadsDirectory + "/" + uuid.toString() + "__processed.jpg", annotations, Annotator.DEFAULT_IMG_FORMAT);
        return ImageIO.read(new File(annotated));
    }
}
