package uy.com.infuy.jfacedetect.web.rest;

import uy.com.infuy.jfacedetect.JhipsterApp;

import uy.com.infuy.jfacedetect.domain.Face;
import uy.com.infuy.jfacedetect.repository.FaceRepository;
import uy.com.infuy.jfacedetect.service.FaceService;
import uy.com.infuy.jfacedetect.service.dto.FaceDTO;
import uy.com.infuy.jfacedetect.service.mapper.FaceMapper;
import uy.com.infuy.jfacedetect.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static uy.com.infuy.jfacedetect.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FaceResource REST controller.
 *
 * @see FaceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class FaceResourceIntTest {

    private static final String DEFAULT_PERSISTED_FACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSISTED_FACE_ID = "BBBBBBBBBB";

    @Autowired
    private FaceRepository faceRepository;

    @Autowired
    private FaceMapper faceMapper;

    @Autowired
    private FaceService faceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFaceMockMvc;

    private Face face;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaceResource faceResource = new FaceResource(faceService);
        this.restFaceMockMvc = MockMvcBuilders.standaloneSetup(faceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Face createEntity(EntityManager em) {
        Face face = new Face()
            .persistedFaceId(DEFAULT_PERSISTED_FACE_ID);
        return face;
    }

    @Before
    public void initTest() {
        face = createEntity(em);
    }

    @Test
    @Transactional
    public void createFace() throws Exception {
        int databaseSizeBeforeCreate = faceRepository.findAll().size();

        // Create the Face
        FaceDTO faceDTO = faceMapper.toDto(face);
        restFaceMockMvc.perform(post("/api/faces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faceDTO)))
            .andExpect(status().isCreated());

        // Validate the Face in the database
        List<Face> faceList = faceRepository.findAll();
        assertThat(faceList).hasSize(databaseSizeBeforeCreate + 1);
        Face testFace = faceList.get(faceList.size() - 1);
        assertThat(testFace.getPersistedFaceId()).isEqualTo(DEFAULT_PERSISTED_FACE_ID);
    }

    @Test
    @Transactional
    public void createFaceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faceRepository.findAll().size();

        // Create the Face with an existing ID
        face.setId(1L);
        FaceDTO faceDTO = faceMapper.toDto(face);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaceMockMvc.perform(post("/api/faces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Face in the database
        List<Face> faceList = faceRepository.findAll();
        assertThat(faceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFaces() throws Exception {
        // Initialize the database
        faceRepository.saveAndFlush(face);

        // Get all the faceList
        restFaceMockMvc.perform(get("/api/faces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(face.getId().intValue())))
            .andExpect(jsonPath("$.[*].persistedFaceId").value(hasItem(DEFAULT_PERSISTED_FACE_ID.toString())));
    }

    @Test
    @Transactional
    public void getFace() throws Exception {
        // Initialize the database
        faceRepository.saveAndFlush(face);

        // Get the face
        restFaceMockMvc.perform(get("/api/faces/{id}", face.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(face.getId().intValue()))
            .andExpect(jsonPath("$.persistedFaceId").value(DEFAULT_PERSISTED_FACE_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFace() throws Exception {
        // Get the face
        restFaceMockMvc.perform(get("/api/faces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFace() throws Exception {
        // Initialize the database
        faceRepository.saveAndFlush(face);
        int databaseSizeBeforeUpdate = faceRepository.findAll().size();

        // Update the face
        Face updatedFace = faceRepository.findOne(face.getId());
        // Disconnect from session so that the updates on updatedFace are not directly saved in db
        em.detach(updatedFace);
        updatedFace
            .persistedFaceId(UPDATED_PERSISTED_FACE_ID);
        FaceDTO faceDTO = faceMapper.toDto(updatedFace);

        restFaceMockMvc.perform(put("/api/faces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faceDTO)))
            .andExpect(status().isOk());

        // Validate the Face in the database
        List<Face> faceList = faceRepository.findAll();
        assertThat(faceList).hasSize(databaseSizeBeforeUpdate);
        Face testFace = faceList.get(faceList.size() - 1);
        assertThat(testFace.getPersistedFaceId()).isEqualTo(UPDATED_PERSISTED_FACE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingFace() throws Exception {
        int databaseSizeBeforeUpdate = faceRepository.findAll().size();

        // Create the Face
        FaceDTO faceDTO = faceMapper.toDto(face);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFaceMockMvc.perform(put("/api/faces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faceDTO)))
            .andExpect(status().isCreated());

        // Validate the Face in the database
        List<Face> faceList = faceRepository.findAll();
        assertThat(faceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFace() throws Exception {
        // Initialize the database
        faceRepository.saveAndFlush(face);
        int databaseSizeBeforeDelete = faceRepository.findAll().size();

        // Get the face
        restFaceMockMvc.perform(delete("/api/faces/{id}", face.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Face> faceList = faceRepository.findAll();
        assertThat(faceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Face.class);
        Face face1 = new Face();
        face1.setId(1L);
        Face face2 = new Face();
        face2.setId(face1.getId());
        assertThat(face1).isEqualTo(face2);
        face2.setId(2L);
        assertThat(face1).isNotEqualTo(face2);
        face1.setId(null);
        assertThat(face1).isNotEqualTo(face2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaceDTO.class);
        FaceDTO faceDTO1 = new FaceDTO();
        faceDTO1.setId(1L);
        FaceDTO faceDTO2 = new FaceDTO();
        assertThat(faceDTO1).isNotEqualTo(faceDTO2);
        faceDTO2.setId(faceDTO1.getId());
        assertThat(faceDTO1).isEqualTo(faceDTO2);
        faceDTO2.setId(2L);
        assertThat(faceDTO1).isNotEqualTo(faceDTO2);
        faceDTO1.setId(null);
        assertThat(faceDTO1).isNotEqualTo(faceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(faceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(faceMapper.fromId(null)).isNull();
    }
}
