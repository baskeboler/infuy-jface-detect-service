package uy.com.infuy.jfacedetect.service;

import uy.com.infuy.jfacedetect.service.dto.PersonGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonGroup.
 */
public interface PersonGroupService {

    /**
     * Save a personGroup.
     *
     * @param personGroupDTO the entity to save
     * @return the persisted entity
     */
    PersonGroupDTO save(PersonGroupDTO personGroupDTO);

    /**
     * Get all the personGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PersonGroupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personGroup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PersonGroupDTO findOne(Long id);

    /**
     * Delete the "id" personGroup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
