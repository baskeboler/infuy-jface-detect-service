package uy.com.infuy.jfacedetect.service.mapper;

import uy.com.infuy.jfacedetect.domain.*;
import uy.com.infuy.jfacedetect.service.dto.ImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Image and its DTO ImageDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {


    @Mapping(target = "faces", ignore = true)
    Image toEntity(ImageDTO imageDTO);

    @Mapping(target = "path", expression = "java(String.format(\"/api/files/images/%d\", entity.getId()))")
    ImageDTO toDto(Image entity);

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }
}
