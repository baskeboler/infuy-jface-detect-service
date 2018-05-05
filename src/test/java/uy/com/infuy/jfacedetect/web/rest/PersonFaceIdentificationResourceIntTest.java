package uy.com.infuy.jfacedetect.web.rest;

import uy.com.infuy.jfacedetect.JhipsterApp;

import uy.com.infuy.jfacedetect.domain.PersonFaceIdentification;
import uy.com.infuy.jfacedetect.repository.PersonFaceIdentificationRepository;
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
 * Test class for the PersonFaceIdentificationResource REST controller.
 *
 * @see PersonFaceIdentificationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class PersonFaceIdentificationResourceIntTest {

    private static final Long DEFAULT_ID_PERSON = 1L;
    private static final Long UPDATED_ID_PERSON = 2L;

    private static final Long DEFAULT_ID_FACE_DETECTION = 1L;
    private static final Long UPDATED_ID_FACE_DETECTION = 2L;

    @Autowired
    private PersonFaceIdentificationRepository personFaceIdentificationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonFaceIdentificationMockMvc;

    private PersonFaceIdentification personFaceIdentification;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonFaceIdentificationResource personFaceIdentificationResource = new PersonFaceIdentificationResource(personFaceIdentificationRepository);
        this.restPersonFaceIdentificationMockMvc = MockMvcBuilders.standaloneSetup(personFaceIdentificationResource)
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
    public static PersonFaceIdentification createEntity(EntityManager em) {
        PersonFaceIdentification personFaceIdentification = new PersonFaceIdentification()
            .idPerson(DEFAULT_ID_PERSON)
            .idFaceDetection(DEFAULT_ID_FACE_DETECTION);
        return personFaceIdentification;
    }

    @Before
    public void initTest() {
        personFaceIdentification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonFaceIdentification() throws Exception {
        int databaseSizeBeforeCreate = personFaceIdentificationRepository.findAll().size();

        // Create the PersonFaceIdentification
        restPersonFaceIdentificationMockMvc.perform(post("/api/person-face-identifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceIdentification)))
            .andExpect(status().isCreated());

        // Validate the PersonFaceIdentification in the database
        List<PersonFaceIdentification> personFaceIdentificationList = personFaceIdentificationRepository.findAll();
        assertThat(personFaceIdentificationList).hasSize(databaseSizeBeforeCreate + 1);
        PersonFaceIdentification testPersonFaceIdentification = personFaceIdentificationList.get(personFaceIdentificationList.size() - 1);
        assertThat(testPersonFaceIdentification.getIdPerson()).isEqualTo(DEFAULT_ID_PERSON);
        assertThat(testPersonFaceIdentification.getIdFaceDetection()).isEqualTo(DEFAULT_ID_FACE_DETECTION);
    }

    @Test
    @Transactional
    public void createPersonFaceIdentificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personFaceIdentificationRepository.findAll().size();

        // Create the PersonFaceIdentification with an existing ID
        personFaceIdentification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonFaceIdentificationMockMvc.perform(post("/api/person-face-identifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceIdentification)))
            .andExpect(status().isBadRequest());

        // Validate the PersonFaceIdentification in the database
        List<PersonFaceIdentification> personFaceIdentificationList = personFaceIdentificationRepository.findAll();
        assertThat(personFaceIdentificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonFaceIdentifications() throws Exception {
        // Initialize the database
        personFaceIdentificationRepository.saveAndFlush(personFaceIdentification);

        // Get all the personFaceIdentificationList
        restPersonFaceIdentificationMockMvc.perform(get("/api/person-face-identifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personFaceIdentification.getId().intValue())))
            .andExpect(jsonPath("$.[*].idPerson").value(hasItem(DEFAULT_ID_PERSON.intValue())))
            .andExpect(jsonPath("$.[*].idFaceDetection").value(hasItem(DEFAULT_ID_FACE_DETECTION.intValue())));
    }

    @Test
    @Transactional
    public void getPersonFaceIdentification() throws Exception {
        // Initialize the database
        personFaceIdentificationRepository.saveAndFlush(personFaceIdentification);

        // Get the personFaceIdentification
        restPersonFaceIdentificationMockMvc.perform(get("/api/person-face-identifications/{id}", personFaceIdentification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personFaceIdentification.getId().intValue()))
            .andExpect(jsonPath("$.idPerson").value(DEFAULT_ID_PERSON.intValue()))
            .andExpect(jsonPath("$.idFaceDetection").value(DEFAULT_ID_FACE_DETECTION.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonFaceIdentification() throws Exception {
        // Get the personFaceIdentification
        restPersonFaceIdentificationMockMvc.perform(get("/api/person-face-identifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonFaceIdentification() throws Exception {
        // Initialize the database
        personFaceIdentificationRepository.saveAndFlush(personFaceIdentification);
        int databaseSizeBeforeUpdate = personFaceIdentificationRepository.findAll().size();

        // Update the personFaceIdentification
        PersonFaceIdentification updatedPersonFaceIdentification = personFaceIdentificationRepository.findOne(personFaceIdentification.getId());
        // Disconnect from session so that the updates on updatedPersonFaceIdentification are not directly saved in db
        em.detach(updatedPersonFaceIdentification);
        updatedPersonFaceIdentification
            .idPerson(UPDATED_ID_PERSON)
            .idFaceDetection(UPDATED_ID_FACE_DETECTION);

        restPersonFaceIdentificationMockMvc.perform(put("/api/person-face-identifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonFaceIdentification)))
            .andExpect(status().isOk());

        // Validate the PersonFaceIdentification in the database
        List<PersonFaceIdentification> personFaceIdentificationList = personFaceIdentificationRepository.findAll();
        assertThat(personFaceIdentificationList).hasSize(databaseSizeBeforeUpdate);
        PersonFaceIdentification testPersonFaceIdentification = personFaceIdentificationList.get(personFaceIdentificationList.size() - 1);
        assertThat(testPersonFaceIdentification.getIdPerson()).isEqualTo(UPDATED_ID_PERSON);
        assertThat(testPersonFaceIdentification.getIdFaceDetection()).isEqualTo(UPDATED_ID_FACE_DETECTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonFaceIdentification() throws Exception {
        int databaseSizeBeforeUpdate = personFaceIdentificationRepository.findAll().size();

        // Create the PersonFaceIdentification

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonFaceIdentificationMockMvc.perform(put("/api/person-face-identifications")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personFaceIdentification)))
            .andExpect(status().isCreated());

        // Validate the PersonFaceIdentification in the database
        List<PersonFaceIdentification> personFaceIdentificationList = personFaceIdentificationRepository.findAll();
        assertThat(personFaceIdentificationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonFaceIdentification() throws Exception {
        // Initialize the database
        personFaceIdentificationRepository.saveAndFlush(personFaceIdentification);
        int databaseSizeBeforeDelete = personFaceIdentificationRepository.findAll().size();

        // Get the personFaceIdentification
        restPersonFaceIdentificationMockMvc.perform(delete("/api/person-face-identifications/{id}", personFaceIdentification.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonFaceIdentification> personFaceIdentificationList = personFaceIdentificationRepository.findAll();
        assertThat(personFaceIdentificationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonFaceIdentification.class);
        PersonFaceIdentification personFaceIdentification1 = new PersonFaceIdentification();
        personFaceIdentification1.setId(1L);
        PersonFaceIdentification personFaceIdentification2 = new PersonFaceIdentification();
        personFaceIdentification2.setId(personFaceIdentification1.getId());
        assertThat(personFaceIdentification1).isEqualTo(personFaceIdentification2);
        personFaceIdentification2.setId(2L);
        assertThat(personFaceIdentification1).isNotEqualTo(personFaceIdentification2);
        personFaceIdentification1.setId(null);
        assertThat(personFaceIdentification1).isNotEqualTo(personFaceIdentification2);
    }
}
