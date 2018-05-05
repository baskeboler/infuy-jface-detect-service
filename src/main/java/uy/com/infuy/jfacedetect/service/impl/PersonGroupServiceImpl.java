package uy.com.infuy.jfacedetect.service.impl;

import org.springframework.util.StringUtils;
import uy.com.infuy.jfacedetect.service.AzureFaceApiService;
import uy.com.infuy.jfacedetect.service.PersonGroupService;
import uy.com.infuy.jfacedetect.domain.PersonGroup;
import uy.com.infuy.jfacedetect.repository.PersonGroupRepository;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupDTO;
import uy.com.infuy.jfacedetect.service.mapper.PersonGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PersonGroup.
 */
@Service
@Transactional
public class PersonGroupServiceImpl implements PersonGroupService {

    private final Logger log = LoggerFactory.getLogger(PersonGroupServiceImpl.class);

    private final PersonGroupRepository personGroupRepository;

    private final PersonGroupMapper personGroupMapper;

    private final AzureFaceApiService faceApiService;

    public PersonGroupServiceImpl(PersonGroupRepository personGroupRepository, PersonGroupMapper personGroupMapper, AzureFaceApiService azureFaceApiService) {
        this.personGroupRepository = personGroupRepository;
        this.personGroupMapper = personGroupMapper;
        this.faceApiService = azureFaceApiService;
    }

    /**
     * Save a personGroup.
     *
     * @param personGroupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PersonGroupDTO save(PersonGroupDTO personGroupDTO) {
        log.debug("Request to save PersonGroup : {}", personGroupDTO);
        PersonGroup personGroup = personGroupMapper.toEntity(personGroupDTO);
        if (!StringUtils.isEmpty(personGroup.getPersonGroupId() )) {
            log.warn("personGroupId is not null!");
            personGroup.setPersonGroupId(null);
        }
        faceApiService.createPersonGroup(personGroup);
        personGroup = personGroupRepository.save(personGroup);
        return personGroupMapper.toDto(personGroup);
    }

    /**
     * Get all the personGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonGroups");
        return personGroupRepository.findAll(pageable)
            .map(personGroupMapper::toDto);
    }

    /**
     * Get one personGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PersonGroupDTO findOne(Long id) {
        log.debug("Request to get PersonGroup : {}", id);
        PersonGroup personGroup = personGroupRepository.findOne(id);
        return personGroupMapper.toDto(personGroup);
    }

    /**
     * Delete the personGroup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonGroup : {}", id);
        personGroupRepository.delete(id);
    }
}
