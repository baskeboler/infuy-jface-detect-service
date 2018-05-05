package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import uy.com.infuy.jfacedetect.domain.PersonFaceSubmission;

import uy.com.infuy.jfacedetect.repository.PersonFaceSubmissionRepository;
import uy.com.infuy.jfacedetect.service.AzureFaceApiService;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonFaceSubmission.
 */
@RestController
@RequestMapping("/api")
public class PersonFaceSubmissionResource {

    private final Logger log = LoggerFactory.getLogger(PersonFaceSubmissionResource.class);

    private static final String ENTITY_NAME = "personFaceSubmission";
    private final AzureFaceApiService azureFaceApiService;
    private final PersonFaceSubmissionRepository personFaceSubmissionRepository;

    public PersonFaceSubmissionResource(AzureFaceApiService azureFaceApiService, PersonFaceSubmissionRepository personFaceSubmissionRepository) {
        this.azureFaceApiService = azureFaceApiService;
        this.personFaceSubmissionRepository = personFaceSubmissionRepository;
    }

    /**
     * POST  /person-face-submissions : Create a new personFaceSubmission.
     *
     * @param personFaceSubmission the personFaceSubmission to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personFaceSubmission, or with status 400 (Bad Request) if the personFaceSubmission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-face-submissions")
    @Timed
    public ResponseEntity<PersonFaceSubmission> createPersonFaceSubmission(@RequestBody PersonFaceSubmission personFaceSubmission) throws URISyntaxException, IOException {
        log.debug("REST request to save PersonFaceSubmission : {}", personFaceSubmission);
        if (personFaceSubmission.getId() != null) {
            throw new BadRequestAlertException("A new personFaceSubmission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonFaceSubmission result = azureFaceApiService.addFaceWithFaceDetection(
            personFaceSubmission.getFaceDetectionId(),
            personFaceSubmission.getPersonId());

        result = personFaceSubmissionRepository.save(result);
        return ResponseEntity.created(new URI("/api/person-face-submissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-face-submissions : Updates an existing personFaceSubmission.
     *
     * @param personFaceSubmission the personFaceSubmission to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personFaceSubmission,
     * or with status 400 (Bad Request) if the personFaceSubmission is not valid,
     * or with status 500 (Internal Server Error) if the personFaceSubmission couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-face-submissions")
    @Timed
    public ResponseEntity<PersonFaceSubmission> updatePersonFaceSubmission(@RequestBody PersonFaceSubmission personFaceSubmission) throws URISyntaxException, IOException {
        log.debug("REST request to update PersonFaceSubmission : {}", personFaceSubmission);
        if (personFaceSubmission.getId() == null) {
            return createPersonFaceSubmission(personFaceSubmission);
        }
        PersonFaceSubmission result = personFaceSubmissionRepository.save(personFaceSubmission);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personFaceSubmission.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-face-submissions : get all the personFaceSubmissions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personFaceSubmissions in body
     */
    @GetMapping("/person-face-submissions")
    @Timed
    public List<PersonFaceSubmission> getAllPersonFaceSubmissions() {
        log.debug("REST request to get all PersonFaceSubmissions");
        return personFaceSubmissionRepository.findAll();
    }

    /**
     * GET  /person-face-submissions/:id : get the "id" personFaceSubmission.
     *
     * @param id the id of the personFaceSubmission to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personFaceSubmission, or with status 404 (Not Found)
     */
    @GetMapping("/person-face-submissions/{id}")
    @Timed
    public ResponseEntity<PersonFaceSubmission> getPersonFaceSubmission(@PathVariable Long id) {
        log.debug("REST request to get PersonFaceSubmission : {}", id);
        PersonFaceSubmission personFaceSubmission = personFaceSubmissionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personFaceSubmission));
    }

    /**
     * DELETE  /person-face-submissions/:id : delete the "id" personFaceSubmission.
     *
     * @param id the id of the personFaceSubmission to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-face-submissions/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonFaceSubmission(@PathVariable Long id) {
        log.debug("REST request to delete PersonFaceSubmission : {}", id);
        personFaceSubmissionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
