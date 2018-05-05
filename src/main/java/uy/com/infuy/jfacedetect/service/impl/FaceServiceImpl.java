package uy.com.infuy.jfacedetect.service.impl;

import uy.com.infuy.jfacedetect.service.FaceService;
import uy.com.infuy.jfacedetect.domain.Face;
import uy.com.infuy.jfacedetect.repository.FaceRepository;
import uy.com.infuy.jfacedetect.service.dto.FaceDTO;
import uy.com.infuy.jfacedetect.service.mapper.FaceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Face.
 */
@Service
@Transactional
public class FaceServiceImpl implements FaceService {

    private final Logger log = LoggerFactory.getLogger(FaceServiceImpl.class);

    private final FaceRepository faceRepository;

    private final FaceMapper faceMapper;

    public FaceServiceImpl(FaceRepository faceRepository, FaceMapper faceMapper) {
        this.faceRepository = faceRepository;
        this.faceMapper = faceMapper;
    }

    /**
     * Save a face.
     *
     * @param faceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public FaceDTO save(FaceDTO faceDTO) {
        log.debug("Request to save Face : {}", faceDTO);
        Face face = faceMapper.toEntity(faceDTO);
        face = faceRepository.save(face);
        return faceMapper.toDto(face);
    }

    /**
     * Get all the faces.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Faces");
        return faceRepository.findAll(pageable)
            .map(faceMapper::toDto);
    }

    /**
     * Get one face by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FaceDTO findOne(Long id) {
        log.debug("Request to get Face : {}", id);
        Face face = faceRepository.findOne(id);
        return faceMapper.toDto(face);
    }

    /**
     * Delete the face by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Face : {}", id);
        faceRepository.delete(id);
    }
}
