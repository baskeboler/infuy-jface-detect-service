package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import uy.com.infuy.jfacedetect.service.AzureFaceApiService;
import uy.com.infuy.jfacedetect.service.PersonGroupService;
import uy.com.infuy.jfacedetect.service.dto.TrainingStatusDTO;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import uy.com.infuy.jfacedetect.web.rest.util.PaginationUtil;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PersonGroup.
 */
@RestController
@RequestMapping("/api")
public class PersonGroupResource {

    private final Logger log = LoggerFactory.getLogger(PersonGroupResource.class);

    private static final String ENTITY_NAME = "personGroup";

    private final PersonGroupService personGroupService;
    private final AzureFaceApiService faceApiService;
    public PersonGroupResource(PersonGroupService personGroupService, AzureFaceApiService faceApiService) {
        this.personGroupService = personGroupService;
        this.faceApiService = faceApiService;
    }

    /**
     * POST  /person-groups : Create a new personGroup.
     *
     * @param personGroupDTO the personGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personGroupDTO, or with status 400 (Bad Request) if the personGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-groups")
    @Timed
    public ResponseEntity<PersonGroupDTO> createPersonGroup(@RequestBody PersonGroupDTO personGroupDTO) throws URISyntaxException {
        log.debug("REST request to save PersonGroup : {}", personGroupDTO);
        if (personGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new personGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }


        PersonGroupDTO result = personGroupService.save(personGroupDTO);
        return ResponseEntity.created(new URI("/api/person-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-groups : Updates an existing personGroup.
     *
     * @param personGroupDTO the personGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personGroupDTO,
     * or with status 400 (Bad Request) if the personGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the personGroupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-groups")
    @Timed
    public ResponseEntity<PersonGroupDTO> updatePersonGroup(@RequestBody PersonGroupDTO personGroupDTO) throws URISyntaxException {
        log.debug("REST request to update PersonGroup : {}", personGroupDTO);
        if (personGroupDTO.getId() == null) {
            return createPersonGroup(personGroupDTO);
        }
        PersonGroupDTO result = personGroupService.save(personGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-groups : get all the personGroups.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personGroups in body
     */
    @GetMapping("/person-groups")
    @Timed
    public ResponseEntity<List<PersonGroupDTO>> getAllPersonGroups(Pageable pageable) {
        log.debug("REST request to get a page of PersonGroups");
        Page<PersonGroupDTO> page = personGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-groups/:id : get the "id" personGroup.
     *
     * @param id the id of the personGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-groups/{id}")
    @Timed
    public ResponseEntity<PersonGroupDTO> getPersonGroup(@PathVariable Long id) {
        log.debug("REST request to get PersonGroup : {}", id);
        PersonGroupDTO personGroupDTO = personGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personGroupDTO));
    }

    @PostMapping("/person-groups/{id}/train")
    @Timed
    public ResponseEntity<Void> trainGroup(@PathVariable Long id) {
        log.debug("REST request to train person group {}", id);
        faceApiService.trainPersonGroup(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/person-groups/{id}/training-status")
    @Timed
    public ResponseEntity<TrainingStatusDTO> getTrainingStatus(@PathVariable Long id){
        log.debug("REST request to get training status for person group {}", id);
        TrainingStatusDTO trainingStatus = faceApiService.getTrainingStatus(id);
        return ResponseEntity.ok(trainingStatus);
    }
    /**
     * DELETE  /person-groups/:id : delete the "id" personGroup.
     *
     * @param id the id of the personGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-groups/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonGroup(@PathVariable Long id) {
        log.debug("REST request to delete PersonGroup : {}", id);
        personGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
