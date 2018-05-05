package uy.com.infuy.jfacedetect.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import uy.com.infuy.jfacedetect.config.ApplicationProperties;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.domain.PersonFaceSubmission;
import uy.com.infuy.jfacedetect.repository.FaceDetectionRepository;
import uy.com.infuy.jfacedetect.repository.PersonFaceSubmissionRepository;
import uy.com.infuy.jfacedetect.service.FaceDetectionService;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;
import uy.com.infuy.jfacedetect.service.mapper.FaceDetectionMapper;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FaceDetectionServiceImpl implements FaceDetectionService {
    public static final Logger logger = LoggerFactory.getLogger(FaceDetectionServiceImpl.class);

    private final FaceDetectionMapper mapper;
    private final FaceDetectionRepository repository;
    private final ApplicationProperties properties;
    private final PersonFaceSubmissionRepository faceSubmissionRepository;

    public FaceDetectionServiceImpl(FaceDetectionMapper mapper, FaceDetectionRepository repository, ApplicationProperties properties, PersonFaceSubmissionRepository faceSubmissionRepository) {
        this.mapper = mapper;
        this.repository = repository;
        this.properties = properties;
        this.faceSubmissionRepository = faceSubmissionRepository;
    }

    @PostConstruct
    public void init() {
        logger.info("Initializing Face Detection Service");
    }

    @Override
    public FaceDetectionDTO save(FaceDetectionDTO faceDetectionDTO) {
        FaceDetection entity = mapper.toEntity(faceDetectionDTO);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FaceDetectionDTO> findOne(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FaceDetection> findOneByFaceId(String faceId) {
        return repository.findOneByFaceId(faceId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FaceDetectionDTO> getAll() {
        return repository.findAll()
            .stream().map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FaceDetectionDTO> getAllValid() {
//        faceSubmissionRepository.
        return repository.findAll().stream()
            .filter(fd -> {
                File file = new File(
                    properties.getFaces().getPath(),
                    String.format("%s.jpg", fd.getFaceId()));
                return file.exists();

            })
            .filter(fd ->
                faceSubmissionRepository.findByFaceDetectionId(fd.getId()).isPresent() == false
            )
            .map(mapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FaceDetectionDTO> get(@PathVariable("face_id") Long faceId) {
        return repository.findById(faceId).map(mapper::toDto);
    }
}
