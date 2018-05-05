package uy.com.infuy.jfacedetect.repository;

import uy.com.infuy.jfacedetect.domain.PersonGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PersonGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonGroupRepository extends JpaRepository<PersonGroup, Long> {

    Optional<PersonGroup> findById(Long id);
}
