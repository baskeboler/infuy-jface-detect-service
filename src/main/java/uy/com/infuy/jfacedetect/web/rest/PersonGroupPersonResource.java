package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import uy.com.infuy.jfacedetect.service.PersonGroupPersonService;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import uy.com.infuy.jfacedetect.web.rest.util.PaginationUtil;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupPersonDTO;
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
 * REST controller for managing PersonGroupPerson.
 */
@RestController
@RequestMapping("/api")
public class PersonGroupPersonResource {

    private final Logger log = LoggerFactory.getLogger(PersonGroupPersonResource.class);

    private static final String ENTITY_NAME = "personGroupPerson";

    private final PersonGroupPersonService personGroupPersonService;

    public PersonGroupPersonResource(PersonGroupPersonService personGroupPersonService) {
        this.personGroupPersonService = personGroupPersonService;
    }

    /**
     * POST  /person-group-people : Create a new personGroupPerson.
     *
     * @param personGroupPersonDTO the personGroupPersonDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personGroupPersonDTO, or with status 400 (Bad Request) if the personGroupPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/person-group-people")
    @Timed
    public ResponseEntity<PersonGroupPersonDTO> createPersonGroupPerson(@RequestBody PersonGroupPersonDTO personGroupPersonDTO) throws URISyntaxException {
        log.debug("REST request to save PersonGroupPerson : {}", personGroupPersonDTO);
        if (personGroupPersonDTO.getId() != null) {
            throw new BadRequestAlertException("A new personGroupPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonGroupPersonDTO result = personGroupPersonService.save(personGroupPersonDTO);
        return ResponseEntity.created(new URI("/api/person-group-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /person-group-people : Updates an existing personGroupPerson.
     *
     * @param personGroupPersonDTO the personGroupPersonDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personGroupPersonDTO,
     * or with status 400 (Bad Request) if the personGroupPersonDTO is not valid,
     * or with status 500 (Internal Server Error) if the personGroupPersonDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/person-group-people")
    @Timed
    public ResponseEntity<PersonGroupPersonDTO> updatePersonGroupPerson(@RequestBody PersonGroupPersonDTO personGroupPersonDTO) throws URISyntaxException {
        log.debug("REST request to update PersonGroupPerson : {}", personGroupPersonDTO);
        if (personGroupPersonDTO.getId() == null) {
            return createPersonGroupPerson(personGroupPersonDTO);
        }
        PersonGroupPersonDTO result = personGroupPersonService.save(personGroupPersonDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, personGroupPersonDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /person-group-people : get all the personGroupPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of personGroupPeople in body
     */
    @GetMapping("/person-group-people")
    @Timed
    public ResponseEntity<List<PersonGroupPersonDTO>> getAllPersonGroupPeople(Pageable pageable) {
        log.debug("REST request to get a page of PersonGroupPeople");
        Page<PersonGroupPersonDTO> page = personGroupPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/person-group-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /person-group-people/:id : get the "id" personGroupPerson.
     *
     * @param id the id of the personGroupPersonDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personGroupPersonDTO, or with status 404 (Not Found)
     */
    @GetMapping("/person-group-people/{id}")
    @Timed
    public ResponseEntity<PersonGroupPersonDTO> getPersonGroupPerson(@PathVariable Long id) {
        log.debug("REST request to get PersonGroupPerson : {}", id);
        PersonGroupPersonDTO personGroupPersonDTO = personGroupPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(personGroupPersonDTO));
    }

    /**
     * DELETE  /person-group-people/:id : delete the "id" personGroupPerson.
     *
     * @param id the id of the personGroupPersonDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/person-group-people/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonGroupPerson(@PathVariable Long id) {
        log.debug("REST request to delete PersonGroupPerson : {}", id);
        personGroupPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


}
