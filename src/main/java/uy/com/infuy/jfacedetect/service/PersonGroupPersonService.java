package uy.com.infuy.jfacedetect.service;

import uy.com.infuy.jfacedetect.service.dto.PersonGroupPersonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PersonGroupPerson.
 */
public interface PersonGroupPersonService {

    /**
     * Save a personGroupPerson.
     *
     * @param personGroupPersonDTO the entity to save
     * @return the persisted entity
     */
    PersonGroupPersonDTO save(PersonGroupPersonDTO personGroupPersonDTO);

    /**
     * Get all the personGroupPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PersonGroupPersonDTO> findAll(Pageable pageable);

    /**
     * Get the "id" personGroupPerson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PersonGroupPersonDTO findOne(Long id);

    /**
     * Delete the "id" personGroupPerson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
