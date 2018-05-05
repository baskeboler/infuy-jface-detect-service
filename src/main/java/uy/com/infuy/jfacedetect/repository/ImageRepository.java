package uy.com.infuy.jfacedetect.repository;

import org.springframework.data.repository.query.Param;
import uy.com.infuy.jfacedetect.domain.Image;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Image entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i.id from Image i where i.path like concat('%', :uuid, '.jpg')")
    Optional<Long> findImageIdByFilenameUuid(@Param("uuid") String uuid);

    Optional<Image> findOneById(Long id);
}
