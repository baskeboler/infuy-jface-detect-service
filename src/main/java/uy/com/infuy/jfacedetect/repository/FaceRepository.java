package uy.com.infuy.jfacedetect.repository;

import uy.com.infuy.jfacedetect.domain.Face;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Face entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FaceRepository extends JpaRepository<Face, Long> {

}
