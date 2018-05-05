package uy.com.infuy.jfacedetect.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uy.com.infuy.jfacedetect.service.FaceService;
import uy.com.infuy.jfacedetect.service.dto.FaceDTO;
import uy.com.infuy.jfacedetect.web.rest.errors.BadRequestAlertException;
import uy.com.infuy.jfacedetect.web.rest.util.HeaderUtil;
import uy.com.infuy.jfacedetect.web.rest.util.PaginationUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Face.
 */
@RestController
@RequestMapping("/api")
public class FaceResource {

    private final Logger log = LoggerFactory.getLogger(FaceResource.class);

    private static final String ENTITY_NAME = "face";

    private final FaceService faceService;

    public FaceResource(FaceService faceService) {
        this.faceService = faceService;
    }

    /**
     * POST  /faces : Create a new face.
     *
     * @param faceDTO the faceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faceDTO, or with status 400 (Bad Request) if the face has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/faces")
    @Timed
    public ResponseEntity<FaceDTO> createFace(@RequestBody FaceDTO faceDTO) throws URISyntaxException {
        log.debug("REST request to save Face : {}", faceDTO);
        if (faceDTO.getId() != null) {
            throw new BadRequestAlertException("A new face cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaceDTO result = faceService.save(faceDTO);
        return ResponseEntity.created(new URI("/api/faces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /faces : Updates an existing face.
     *
     * @param faceDTO the faceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faceDTO,
     * or with status 400 (Bad Request) if the faceDTO is not valid,
     * or with status 500 (Internal Server Error) if the faceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/faces")
    @Timed
    public ResponseEntity<FaceDTO> updateFace(@RequestBody FaceDTO faceDTO) throws URISyntaxException {
        log.debug("REST request to update Face : {}", faceDTO);
        if (faceDTO.getId() == null) {
            return createFace(faceDTO);
        }
        FaceDTO result = faceService.save(faceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /faces : get all the faces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of faces in body
     */
    @GetMapping("/faces")
    @Timed
    public ResponseEntity<List<FaceDTO>> getAllFaces(Pageable pageable) {
        log.debug("REST request to get a page of Faces");
        Page<FaceDTO> page = faceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/faces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /faces/:id : get the "id" face.
     *
     * @param id the id of the faceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/faces/{id}")
    @Timed
    public ResponseEntity<FaceDTO> getFace(@PathVariable Long id) {
        log.debug("REST request to get Face : {}", id);
        FaceDTO faceDTO = faceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faceDTO));
    }

    /**
     * DELETE  /faces/:id : delete the "id" face.
     *
     * @param id the id of the faceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/faces/{id}")
    @Timed
    public ResponseEntity<Void> deleteFace(@PathVariable Long id) {
        log.debug("REST request to delete Face : {}", id);
        faceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
