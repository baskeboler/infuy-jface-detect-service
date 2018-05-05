package uy.com.infuy.jfacedetect.web.rest;

import uy.com.infuy.jfacedetect.JhipsterApp;

import uy.com.infuy.jfacedetect.domain.PersonGroup;
import uy.com.infuy.jfacedetect.repository.PersonGroupRepository;
import uy.com.infuy.jfacedetect.service.PersonGroupService;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupDTO;
import uy.com.infuy.jfacedetect.service.mapper.PersonGroupMapper;
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
 * Test class for the PersonGroupResource REST controller.
 *
 * @see PersonGroupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterApp.class)
public class PersonGroupResourceIntTest {

    private static final String DEFAULT_PERSON_GROUP_ID = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_GROUP_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PersonGroupRepository personGroupRepository;

    @Autowired
    private PersonGroupMapper personGroupMapper;

    @Autowired
    private PersonGroupService personGroupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonGroupMockMvc;

    private PersonGroup personGroup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonGroupResource personGroupResource = new PersonGroupResource(personGroupService, null);
        this.restPersonGroupMockMvc = MockMvcBuilders.standaloneSetup(personGroupResource)
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
    public static PersonGroup createEntity(EntityManager em) {
        PersonGroup personGroup = new PersonGroup()
            .personGroupId(DEFAULT_PERSON_GROUP_ID)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return personGroup;
    }

    @Before
    public void initTest() {
        personGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonGroup() throws Exception {
        int databaseSizeBeforeCreate = personGroupRepository.findAll().size();

        // Create the PersonGroup
        PersonGroupDTO personGroupDTO = personGroupMapper.toDto(personGroup);
        restPersonGroupMockMvc.perform(post("/api/person-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonGroup in the database
        List<PersonGroup> personGroupList = personGroupRepository.findAll();
        assertThat(personGroupList).hasSize(databaseSizeBeforeCreate + 1);
        PersonGroup testPersonGroup = personGroupList.get(personGroupList.size() - 1);
        assertThat(testPersonGroup.getPersonGroupId()).isEqualTo(DEFAULT_PERSON_GROUP_ID);
        assertThat(testPersonGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPersonGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personGroupRepository.findAll().size();

        // Create the PersonGroup with an existing ID
        personGroup.setId(1L);
        PersonGroupDTO personGroupDTO = personGroupMapper.toDto(personGroup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonGroupMockMvc.perform(post("/api/person-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PersonGroup in the database
        List<PersonGroup> personGroupList = personGroupRepository.findAll();
        assertThat(personGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonGroups() throws Exception {
        // Initialize the database
        personGroupRepository.saveAndFlush(personGroup);

        // Get all the personGroupList
        restPersonGroupMockMvc.perform(get("/api/person-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].personGroupId").value(hasItem(DEFAULT_PERSON_GROUP_ID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPersonGroup() throws Exception {
        // Initialize the database
        personGroupRepository.saveAndFlush(personGroup);

        // Get the personGroup
        restPersonGroupMockMvc.perform(get("/api/person-groups/{id}", personGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personGroup.getId().intValue()))
            .andExpect(jsonPath("$.personGroupId").value(DEFAULT_PERSON_GROUP_ID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonGroup() throws Exception {
        // Get the personGroup
        restPersonGroupMockMvc.perform(get("/api/person-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonGroup() throws Exception {
        // Initialize the database
        personGroupRepository.saveAndFlush(personGroup);
        int databaseSizeBeforeUpdate = personGroupRepository.findAll().size();

        // Update the personGroup
        PersonGroup updatedPersonGroup = personGroupRepository.findOne(personGroup.getId());
        // Disconnect from session so that the updates on updatedPersonGroup are not directly saved in db
        em.detach(updatedPersonGroup);
        updatedPersonGroup
            .personGroupId(UPDATED_PERSON_GROUP_ID)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        PersonGroupDTO personGroupDTO = personGroupMapper.toDto(updatedPersonGroup);

        restPersonGroupMockMvc.perform(put("/api/person-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupDTO)))
            .andExpect(status().isOk());

        // Validate the PersonGroup in the database
        List<PersonGroup> personGroupList = personGroupRepository.findAll();
        assertThat(personGroupList).hasSize(databaseSizeBeforeUpdate);
        PersonGroup testPersonGroup = personGroupList.get(personGroupList.size() - 1);
        assertThat(testPersonGroup.getPersonGroupId()).isEqualTo(UPDATED_PERSON_GROUP_ID);
        assertThat(testPersonGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonGroup() throws Exception {
        int databaseSizeBeforeUpdate = personGroupRepository.findAll().size();

        // Create the PersonGroup
        PersonGroupDTO personGroupDTO = personGroupMapper.toDto(personGroup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonGroupMockMvc.perform(put("/api/person-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the PersonGroup in the database
        List<PersonGroup> personGroupList = personGroupRepository.findAll();
        assertThat(personGroupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonGroup() throws Exception {
        // Initialize the database
        personGroupRepository.saveAndFlush(personGroup);
        int databaseSizeBeforeDelete = personGroupRepository.findAll().size();

        // Get the personGroup
        restPersonGroupMockMvc.perform(delete("/api/person-groups/{id}", personGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonGroup> personGroupList = personGroupRepository.findAll();
        assertThat(personGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGroup.class);
        PersonGroup personGroup1 = new PersonGroup();
        personGroup1.setId(1L);
        PersonGroup personGroup2 = new PersonGroup();
        personGroup2.setId(personGroup1.getId());
        assertThat(personGroup1).isEqualTo(personGroup2);
        personGroup2.setId(2L);
        assertThat(personGroup1).isNotEqualTo(personGroup2);
        personGroup1.setId(null);
        assertThat(personGroup1).isNotEqualTo(personGroup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonGroupDTO.class);
        PersonGroupDTO personGroupDTO1 = new PersonGroupDTO();
        personGroupDTO1.setId(1L);
        PersonGroupDTO personGroupDTO2 = new PersonGroupDTO();
        assertThat(personGroupDTO1).isNotEqualTo(personGroupDTO2);
        personGroupDTO2.setId(personGroupDTO1.getId());
        assertThat(personGroupDTO1).isEqualTo(personGroupDTO2);
        personGroupDTO2.setId(2L);
        assertThat(personGroupDTO1).isNotEqualTo(personGroupDTO2);
        personGroupDTO1.setId(null);
        assertThat(personGroupDTO1).isNotEqualTo(personGroupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(personGroupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(personGroupMapper.fromId(null)).isNull();
    }
}
