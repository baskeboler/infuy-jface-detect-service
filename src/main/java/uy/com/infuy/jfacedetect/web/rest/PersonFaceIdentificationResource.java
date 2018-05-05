package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import uy.com.infuy.jfacedetect.domain.PersonFaceIdentification;

import uy.com.infuy.jfacedetect.repository.PersonFaceIdentificationRepository;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonFaceIdentification.
 */
@RestController
@RequestMapping("/api")
public class PersonFaceIdentificationResource {

    private final Logger log = LoggerFactory.getLogger(PersonFaceIdentificationResource.class);

    private static final String ENTITY_NAME = "personFaceIdentification";

    private final PersonFaceIdentificationRepository personFaceIdentificationRepository;

    public PersonFaceIdentificationResource(PersonFaceIdentificationRepository personFaceIdentificationRepository) {
        this.personFaceIdentificationRepository = personFaceIdentificationRepository;
    }

    /**
     * POST  /person-face-identifications : Create a new personFaceIdentification.
     *
     * @param personFaceIdentification the personFaceIdentification to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFaceIdentification, or with status 400 (Bad Request) if the personFaceIdentification has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-face-identifications")
    @Timed
    public ResponseEntity<PersonFaceIdentification> createPersonFaceIdentification(@RequestBody PersonFaceIdentification personFaceIdentification) throws URISyntaxException {
        log.debug("REST request to save PersonFaceIdentification : {}", personFaceIdentification);
        if (personFaceIdentification.getId() != null) {
            throw new BadRequestAlertException("A new personFaceIdentification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonFaceIdentification result = personFaceIdentificationRepository.save(personFaceIdentification);
        return ResponseEntity.created(new URI("/api/person-face-identifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-face-identifications : Updates an existing personFaceIdentification.
     *
     * @param personFaceIdentification the personFaceIdentification to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFaceIdentification,
     * or with status 400 (Bad Request) if the personFaceIdentification is not valid,
     * or with status 500 (Internal Server Error) if the personFaceIdentification couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-face-identifications")
    @Timed
    public ResponseEntity<PersonFaceIdentification> updatePersonFaceIdentification(@RequestBody PersonFaceIdentification personFaceIdentification) throws URISyntaxException {
        log.debug("REST request to update PersonFaceIdentification : {}", personFaceIdentification);
        if (personFaceIdentification.getId() == null) {
            return createPersonFaceIdentification(personFaceIdentification);
        }
        PersonFaceIdentification result = personFaceIdentificationRepository.save(personFaceIdentification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personFaceIdentification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-face-identifications : get all the personFaceIdentifications.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personFaceIdentifications in body
     */
    @GetMapping("/person-face-identifications")
    @Timed
    public List<PersonFaceIdentification> getAllPersonFaceIdentifications() {
        log.debug("REST request to get all PersonFaceIdentifications");
        return personFaceIdentificationRepository.findAll();
        }

    /**
     * GET  /person-face-identifications/:id : get the "id" personFaceIdentification.
     *
     * @param id the id of the personFaceIdentification to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFaceIdentification, or with status 404 (Not Found)
     */
    @GetMapping("/person-face-identifications/{id}")
    @Timed
    public ResponseEntity<PersonFaceIdentification> getPersonFaceIdentification(@PathVariable Long id) {
        log.debug("REST request to get PersonFaceIdentification : {}", id);
        PersonFaceIdentification personFaceIdentification = personFaceIdentificationRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personFaceIdentification));
    }

    /**
     * DELETE  /person-face-identifications/:id : delete the "id" personFaceIdentification.
     *
     * @param id the id of the personFaceIdentification to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-face-identifications/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFaceIdentification(@PathVariable Long id) {
        log.debug("REST request to delete PersonFaceIdentification : {}", id);
        personFaceIdentificationRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
