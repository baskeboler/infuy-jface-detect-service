package uy.com.infuy.jfacedetect.service.impl;

import uy.com.infuy.jfacedetect.domain.PersonGroup;
import uy.com.infuy.jfacedetect.repository.PersonGroupRepository;
import uy.com.infuy.jfacedetect.service.AzureFaceApiService;
import uy.com.infuy.jfacedetect.service.PersonGroupPersonService;
import uy.com.infuy.jfacedetect.domain.PersonGroupPerson;
import uy.com.infuy.jfacedetect.repository.PersonGroupPersonRepository;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupPersonDTO;
import uy.com.infuy.jfacedetect.service.mapper.PersonGroupPersonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PersonGroupPerson.
 */
@Service
@Transactional
public class PersonGroupPersonServiceImpl implements PersonGroupPersonService {

    private final Logger logger = LoggerFactory.getLogger(PersonGroupPersonServiceImpl.class);

    private final PersonGroupPersonRepository personGroupPersonRepository;
    private  final PersonGroupRepository personGroupRepository;
    private final PersonGroupPersonMapper personGroupPersonMapper;

    private final AzureFaceApiService faceApiService;

    public PersonGroupPersonServiceImpl(PersonGroupPersonRepository personGroupPersonRepository, PersonGroupPersonMapper personGroupPersonMapper, AzureFaceApiService azureFaceApiService, PersonGroupRepository groupRepo) {
        this.personGroupPersonRepository = personGroupPersonRepository;
        this.personGroupPersonMapper = personGroupPersonMapper;
        this.faceApiService = azureFaceApiService;
        this.personGroupRepository = groupRepo;
    }

    /**
     * Save a personGroupPerson.
     *
     * @param personGroupPersonDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonGroupPersonDTO save(PersonGroupPersonDTO personGroupPersonDTO) {
        logger.debug("Request to save PersonGroupPerson : {}", personGroupPersonDTO);
        PersonGroupPerson personGroupPerson = personGroupPersonMapper.toEntity(personGroupPersonDTO);

        PersonGroup group = personGroupRepository.getOne(personGroupPerson.getPersonGroup().getId());
        personGroupPerson.setPersonGroup(group);
        faceApiService.createPerson(personGroupPerson);
        personGroupPerson = personGroupPersonRepository.save(personGroupPerson);
        return personGroupPersonMapper.toDto(personGroupPerson);
    }

    /**
     * Get all the personGroupPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonGroupPersonDTO> findAll(Pageable pageable) {
        logger.debug("Request to get all PersonGroupPeople");
        return personGroupPersonRepository.findAll(pageable)
            .map(personGroupPersonMapper::toDto);
    }

    /**
     * Get one personGroupPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonGroupPersonDTO findOne(Long id) {
        logger.debug("Request to get PersonGroupPerson : {}", id);
        PersonGroupPerson personGroupPerson = personGroupPersonRepository.findOne(id);
        return personGroupPersonMapper.toDto(personGroupPerson);
    }

    /**
     * Delete the personGroupPerson by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        logger.debug("Request to delete PersonGroupPerson : {}", id);
        personGroupPersonRepository.delete(id);
    }
}
