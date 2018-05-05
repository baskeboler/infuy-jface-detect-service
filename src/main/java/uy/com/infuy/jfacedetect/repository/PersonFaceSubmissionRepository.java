package uy.com.infuy.jfacedetect.repository;

import uy.com.infuy.jfacedetect.domain.PersonFaceSubmission;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PersonFaceSubmission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonFaceSubmissionRepository extends JpaRepository<PersonFaceSubmission, Long> {

    Optional<PersonFaceSubmission> findByFaceDetectionId(Long faceDetectionId);
//List<>
}
