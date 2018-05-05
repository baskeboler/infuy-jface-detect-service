package uy.com.infuy.jfacedetect.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;
import uy.com.infuy.jfacedetect.service.dto.ImageDTO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Image.
 */
public interface ImageService {

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    ImageDTO save(ImageDTO imageDTO);

    /**
     * Get all the images.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ImageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" image.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ImageDTO findOne(Long id);

    /**
     * Delete the "id" image.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    void processImage(File f) throws IOException;

    void addFaceDetection(Long imageId, Long faceDetectionId);

    @Transactional(readOnly = true)
    Optional<ImageDTO> findByPathUuid(String uuid);

    @Transactional(readOnly = true)
    List<FaceDetectionDTO> getFacesDetected(Long imageId);

    Optional<BufferedImage> getImageFile(Long id);
}
