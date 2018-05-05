package uy.com.infuy.jfacedetect.web.rest;

import uy.com.infuy.jfacedetect.JhipsterApp;

import uy.com.infuy.jfacedetect.domain.PersonFaceSubmission;
import uy.com.infuy.jfacedetect.repository.PersonFaceSubmissionRepository;
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
 * Test class for the PersonFaceSubmissionResource REST controller.
 *
 * @see PersonFaceSubmissionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class PersonFaceSubmissionResourceIntTest {

    private static final String DEFAULT_PERSISTED_FACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSISTED_FACE_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final Long DEFAULT_FACE_DETECTION_ID = 1L;
    private static final Long UPDATED_FACE_DETECTION_ID = 2L;

    @Autowired
    private PersonFaceSubmissionRepository personFaceSubmissionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonFaceSubmissionMockMvc;

    private PersonFaceSubmission personFaceSubmission;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonFaceSubmissionResource personFaceSubmissionResource = new PersonFaceSubmissionResource(null, personFaceSubmissionRepository);
        this.restPersonFaceSubmissionMockMvc = MockMvcBuilders.standaloneSetup(personFaceSubmissionResource)
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
    public static PersonFaceSubmission createEntity(EntityManager em) {
        PersonFaceSubmission personFaceSubmission = new PersonFaceSubmission()
            .persistedFaceId(DEFAULT_PERSISTED_FACE_ID)
            .personId(DEFAULT_PERSON_ID)
            .faceDetectionId(DEFAULT_FACE_DETECTION_ID);
        return personFaceSubmission;
    }

    @Before
    public void initTest() {
        personFaceSubmission = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFaceSubmission() throws Exception {
        int databaseSizeBeforeCreate = personFaceSubmissionRepository.findAll().size();

        // Create the PersonFaceSubmission
        restPersonFaceSubmissionMockMvc.perform(post("/api/person-face-submissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceSubmission)))
            .andExpect(status().isCreated());

        // Validate the PersonFaceSubmission in the database
        List<PersonFaceSubmission> personFaceSubmissionList = personFaceSubmissionRepository.findAll();
        assertThat(personFaceSubmissionList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFaceSubmission testPersonFaceSubmission = personFaceSubmissionList.get(personFaceSubmissionList.size() - 1);
        assertThat(testPersonFaceSubmission.getPersistedFaceId()).isEqualTo(DEFAULT_PERSISTED_FACE_ID);
        assertThat(testPersonFaceSubmission.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testPersonFaceSubmission.getFaceDetectionId()).isEqualTo(DEFAULT_FACE_DETECTION_ID);
    }

    @Test
    @Transactional
    public void createPersonFaceSubmissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFaceSubmissionRepository.findAll().size();

        // Create the PersonFaceSubmission with an existing ID
        personFaceSubmission.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFaceSubmissionMockMvc.perform(post("/api/person-face-submissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceSubmission)))
            .andExpect(status().isBadRequest());

        // Validate the PersonFaceSubmission in the database
        List<PersonFaceSubmission> personFaceSubmissionList = personFaceSubmissionRepository.findAll();
        assertThat(personFaceSubmissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFaceSubmissions() throws Exception {
        // Initialize the database
        personFaceSubmissionRepository.saveAndFlush(personFaceSubmission);

        // Get all the personFaceSubmissionList
        restPersonFaceSubmissionMockMvc.perform(get("/api/person-face-submissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFaceSubmission.getId().intValue())))
            .andExpect(jsonPath("$.[*].persistedFaceId").value(hasItem(DEFAULT_PERSISTED_FACE_ID.toString())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].faceDetectionId").value(hasItem(DEFAULT_FACE_DETECTION_ID.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFaceSubmission() throws Exception {
        // Initialize the database
        personFaceSubmissionRepository.saveAndFlush(personFaceSubmission);

        // Get the personFaceSubmission
        restPersonFaceSubmissionMockMvc.perform(get("/api/person-face-submissions/{id}", personFaceSubmission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFaceSubmission.getId().intValue()))
            .andExpect(jsonPath("$.persistedFaceId").value(DEFAULT_PERSISTED_FACE_ID.toString()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.faceDetectionId").value(DEFAULT_FACE_DETECTION_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFaceSubmission() throws Exception {
        // Get the personFaceSubmission
        restPersonFaceSubmissionMockMvc.perform(get("/api/person-face-submissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFaceSubmission() throws Exception {
        // Initialize the database
        personFaceSubmissionRepository.saveAndFlush(personFaceSubmission);
        int databaseSizeBeforeUpdate = personFaceSubmissionRepository.findAll().size();

        // Update the personFaceSubmission
        PersonFaceSubmission updatedPersonFaceSubmission = personFaceSubmissionRepository.findOne(personFaceSubmission.getId());
        // Disconnect from session so that the updates on updatedPersonFaceSubmission are not directly saved in db
        em.detach(updatedPersonFaceSubmission);
        updatedPersonFaceSubmission
            .persistedFaceId(UPDATED_PERSISTED_FACE_ID)
            .personId(UPDATED_PERSON_ID)
            .faceDetectionId(UPDATED_FACE_DETECTION_ID);

        restPersonFaceSubmissionMockMvc.perform(put("/api/person-face-submissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFaceSubmission)))
            .andExpect(status().isOk());

        // Validate the PersonFaceSubmission in the database
        List<PersonFaceSubmission> personFaceSubmissionList = personFaceSubmissionRepository.findAll();
        assertThat(personFaceSubmissionList).hasSize(databaseSizeBeforeUpdate);
        PersonFaceSubmission testPersonFaceSubmission = personFaceSubmissionList.get(personFaceSubmissionList.size() - 1);
        assertThat(testPersonFaceSubmission.getPersistedFaceId()).isEqualTo(UPDATED_PERSISTED_FACE_ID);
        assertThat(testPersonFaceSubmission.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testPersonFaceSubmission.getFaceDetectionId()).isEqualTo(UPDATED_FACE_DETECTION_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFaceSubmission() throws Exception {
        int databaseSizeBeforeUpdate = personFaceSubmissionRepository.findAll().size();

        // Create the PersonFaceSubmission

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFaceSubmissionMockMvc.perform(put("/api/person-face-submissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceSubmission)))
            .andExpect(status().isCreated());

        // Validate the PersonFaceSubmission in the database
        List<PersonFaceSubmission> personFaceSubmissionList = personFaceSubmissionRepository.findAll();
        assertThat(personFaceSubmissionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFaceSubmission() throws Exception {
        // Initialize the database
        personFaceSubmissionRepository.saveAndFlush(personFaceSubmission);
        int databaseSizeBeforeDelete = personFaceSubmissionRepository.findAll().size();

        // Get the personFaceSubmission
        restPersonFaceSubmissionMockMvc.perform(delete("/api/person-face-submissions/{id}", personFaceSubmission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFaceSubmission> personFaceSubmissionList = personFaceSubmissionRepository.findAll();
        assertThat(personFaceSubmissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonFaceSubmission.class);
        PersonFaceSubmission personFaceSubmission1 = new PersonFaceSubmission();
        personFaceSubmission1.setId(1L);
        PersonFaceSubmission personFaceSubmission2 = new PersonFaceSubmission();
        personFaceSubmission2.setId(personFaceSubmission1.getId());
        assertThat(personFaceSubmission1).isEqualTo(personFaceSubmission2);
        personFaceSubmission2.setId(2L);
        assertThat(personFaceSubmission1).isNotEqualTo(personFaceSubmission2);
        personFaceSubmission1.setId(null);
        assertThat(personFaceSubmission1).isNotEqualTo(personFaceSubmission2);
    }
}
