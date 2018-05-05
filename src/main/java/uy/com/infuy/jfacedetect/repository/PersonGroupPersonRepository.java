package uy.com.infuy.jfacedetect.repository;

import uy.com.infuy.jfacedetect.domain.PersonGroupPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PersonGroupPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonGroupPersonRepository extends JpaRepository<PersonGroupPerson, Long> {
    Optional<PersonGroupPerson> findById(Long id);
}
