package uy.com.infuy.jfacedetect.web.rest;

import uy.com.infuy.jfacedetect.JhipsterApp;

import uy.com.infuy.jfacedetect.domain.PersonGroupPerson;
import uy.com.infuy.jfacedetect.repository.PersonGroupPersonRepository;
import uy.com.infuy.jfacedetect.service.PersonGroupPersonService;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupPersonDTO;
import uy.com.infuy.jfacedetect.service.mapper.PersonGroupPersonMapper;
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
 * Test class for the PersonGroupPersonResource REST controller.
 *
 * @see PersonGroupPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class PersonGroupPersonResourceIntTest {

    private static final String DEFAULT_PERSON_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PersonGroupPersonRepository personGroupPersonRepository;

    @Autowired
    private PersonGroupPersonMapper personGroupPersonMapper;

    @Autowired
    private PersonGroupPersonService personGroupPersonService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonGroupPersonMockMvc;

    private PersonGroupPerson personGroupPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonGroupPersonResource personGroupPersonResource = new PersonGroupPersonResource(personGroupPersonService);
        this.restPersonGroupPersonMockMvc = MockMvcBuilders.standaloneSetup(personGroupPersonResource)
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
    public static PersonGroupPerson createEntity(EntityManager em) {
        PersonGroupPerson personGroupPerson = new PersonGroupPerson()
            .personId(DEFAULT_PERSON_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return personGroupPerson;
    }

    @Before
    public void initTest() {
        personGroupPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonGroupPerson() throws Exception {
        int databaseSizeBeforeCreate = personGroupPersonRepository.findAll().size();

        // Create the PersonGroupPerson
        PersonGroupPersonDTO personGroupPersonDTO = personGroupPersonMapper.toDto(personGroupPerson);
        restPersonGroupPersonMockMvc.perform(post("/api/person-group-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonGroupPerson in the database
        List<PersonGroupPerson> personGroupPersonList = personGroupPersonRepository.findAll();
        assertThat(personGroupPersonList).hasSize(databaseSizeBeforeCreate + 1);
        PersonGroupPerson testPersonGroupPerson = personGroupPersonList.get(personGroupPersonList.size() - 1);
        assertThat(testPersonGroupPerson.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testPersonGroupPerson.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonGroupPerson.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPersonGroupPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personGroupPersonRepository.findAll().size();

        // Create the PersonGroupPerson with an existing ID
        personGroupPerson.setId(1L);
        PersonGroupPersonDTO personGroupPersonDTO = personGroupPersonMapper.toDto(personGroupPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonGroupPersonMockMvc.perform(post("/api/person-group-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonGroupPerson in the database
        List<PersonGroupPerson> personGroupPersonList = personGroupPersonRepository.findAll();
        assertThat(personGroupPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonGroupPeople() throws Exception {
        // Initialize the database
        personGroupPersonRepository.saveAndFlush(personGroupPerson);

        // Get all the personGroupPersonList
        restPersonGroupPersonMockMvc.perform(get("/api/person-group-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personGroupPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonGroupPerson() throws Exception {
        // Initialize the database
        personGroupPersonRepository.saveAndFlush(personGroupPerson);

        // Get the personGroupPerson
        restPersonGroupPersonMockMvc.perform(get("/api/person-group-people/{id}", personGroupPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personGroupPerson.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonGroupPerson() throws Exception {
        // Get the personGroupPerson
        restPersonGroupPersonMockMvc.perform(get("/api/person-group-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonGroupPerson() throws Exception {
        // Initialize the database
        personGroupPersonRepository.saveAndFlush(personGroupPerson);
        int databaseSizeBeforeUpdate = personGroupPersonRepository.findAll().size();

        // Update the personGroupPerson
        PersonGroupPerson updatedPersonGroupPerson = personGroupPersonRepository.findOne(personGroupPerson.getId());
        // Disconnect from session so that the updates on updatedPersonGroupPerson are not directly saved in db
        em.detach(updatedPersonGroupPerson);
        updatedPersonGroupPerson
            .personId(UPDATED_PERSON_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        PersonGroupPersonDTO personGroupPersonDTO = personGroupPersonMapper.toDto(updatedPersonGroupPerson);

        restPersonGroupPersonMockMvc.perform(put("/api/person-group-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupPersonDTO)))
            .andExpect(status().isOk());

        // Validate the PersonGroupPerson in the database
        List<PersonGroupPerson> personGroupPersonList = personGroupPersonRepository.findAll();
        assertThat(personGroupPersonList).hasSize(databaseSizeBeforeUpdate);
        PersonGroupPerson testPersonGroupPerson = personGroupPersonList.get(personGroupPersonList.size() - 1);
        assertThat(testPersonGroupPerson.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testPersonGroupPerson.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonGroupPerson.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonGroupPerson() throws Exception {
        int databaseSizeBeforeUpdate = personGroupPersonRepository.findAll().size();

        // Create the PersonGroupPerson
        PersonGroupPersonDTO personGroupPersonDTO = personGroupPersonMapper.toDto(personGroupPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonGroupPersonMockMvc.perform(put("/api/person-group-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonGroupPerson in the database
        List<PersonGroupPerson> personGroupPersonList = personGroupPersonRepository.findAll();
        assertThat(personGroupPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonGroupPerson() throws Exception {
        // Initialize the database
        personGroupPersonRepository.saveAndFlush(personGroupPerson);
        int databaseSizeBeforeDelete = personGroupPersonRepository.findAll().size();

        // Get the personGroupPerson
        restPersonGroupPersonMockMvc.perform(delete("/api/person-group-people/{id}", personGroupPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonGroupPerson> personGroupPersonList = personGroupPersonRepository.findAll();
        assertThat(personGroupPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGroupPerson.class);
        PersonGroupPerson personGroupPerson1 = new PersonGroupPerson();
        personGroupPerson1.setId(1L);
        PersonGroupPerson personGroupPerson2 = new PersonGroupPerson();
        personGroupPerson2.setId(personGroupPerson1.getId());
        assertThat(personGroupPerson1).isEqualTo(personGroupPerson2);
        personGroupPerson2.setId(2L);
        assertThat(personGroupPerson1).isNotEqualTo(personGroupPerson2);
        personGroupPerson1.setId(null);
        assertThat(personGroupPerson1).isNotEqualTo(personGroupPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGroupPersonDTO.class);
        PersonGroupPersonDTO personGroupPersonDTO1 = new PersonGroupPersonDTO();
        personGroupPersonDTO1.setId(1L);
        PersonGroupPersonDTO personGroupPersonDTO2 = new PersonGroupPersonDTO();
        assertThat(personGroupPersonDTO1).isNotEqualTo(personGroupPersonDTO2);
        personGroupPersonDTO2.setId(personGroupPersonDTO1.getId());
        assertThat(personGroupPersonDTO1).isEqualTo(personGroupPersonDTO2);
        personGroupPersonDTO2.setId(2L);
        assertThat(personGroupPersonDTO1).isNotEqualTo(personGroupPersonDTO2);
        personGroupPersonDTO1.setId(null);
        assertThat(personGroupPersonDTO1).isNotEqualTo(personGroupPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personGroupPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personGroupPersonMapper.fromId(null)).isNull();
    }
}
