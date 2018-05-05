package uy.com.infuy.jfacedetect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uy.com.infuy.jfacedetect.domain.FaceDetection;

import java.util.Optional;


@SuppressWarnings("unused")
@Repository
public interface FaceDetectionRepository extends JpaRepository<FaceDetection, Long> {

    Optional<FaceDetection> findById(Long id);

    Optional<FaceDetection> findOneByFaceId(String faceId);
}
