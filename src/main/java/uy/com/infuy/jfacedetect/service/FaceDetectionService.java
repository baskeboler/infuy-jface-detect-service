package uy.com.infuy.jfacedetect.service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.service.dto.FaceDetectionDTO;

import java.util.List;
import java.util.Optional;

public interface FaceDetectionService {

    FaceDetectionDTO save(FaceDetectionDTO faceDetectionDTO);

    Optional<FaceDetectionDTO> findOne(Long id);

    Optional<FaceDetection> findOneByFaceId(String faceId);

    @Transactional(readOnly = true)
    List<FaceDetectionDTO> getAll();

    @Transactional(readOnly = true)
    List<FaceDetectionDTO> getAllValid();

    @Transactional(readOnly = true)
    Optional<FaceDetectionDTO> get(@PathVariable("face_id") Long faceId);
}
