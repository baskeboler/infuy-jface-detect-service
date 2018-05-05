package uy.com.infuy.jfacedetect.service;

import uy.com.infuy.jfacedetect.service.dto.FaceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Face.
 */
public interface FaceService {

    /**
     * Save a face.
     *
     * @param faceDTO the entity to save
     * @return the persisted entity
     */
    FaceDTO save(FaceDTO faceDTO);

    /**
     * Get all the faces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FaceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" face.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FaceDTO findOne(Long id);

    /**
     * Delete the "id" face.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
