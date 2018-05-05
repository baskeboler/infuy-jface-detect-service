package uy.com.infuy.jfacedetect.repository;

import uy.com.infuy.jfacedetect.domain.PersonFaceIdentification;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PersonFaceIdentification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonFaceIdentificationRepository extends JpaRepository<PersonFaceIdentification, Long> {

}
