package uy.com.infuy.jfacedetect.service.mapper;

import uy.com.infuy.jfacedetect.domain.*;
import uy.com.infuy.jfacedetect.service.dto.FaceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Face and its DTO FaceDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonGroupPersonMapper.class})
public interface FaceMapper extends EntityMapper<FaceDTO, Face> {

    @Mapping(source = "personGroupPerson.id", target = "personGroupPersonId")
    FaceDTO toDto(Face face);

    @Mapping(source = "personGroupPersonId", target = "personGroupPerson")
    Face toEntity(FaceDTO faceDTO);

    default Face fromId(Long id) {
        if (id == null) {
            return null;
        }
        Face face = new Face();
        face.setId(id);
        return face;
    }
}
