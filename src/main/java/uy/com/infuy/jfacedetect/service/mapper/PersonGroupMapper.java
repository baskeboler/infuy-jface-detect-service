package uy.com.infuy.jfacedetect.service.mapper;

import uy.com.infuy.jfacedetect.domain.*;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonGroup and its DTO PersonGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonGroupMapper extends EntityMapper<PersonGroupDTO, PersonGroup> {


    @Mapping(target = "people", ignore = true)
    PersonGroup toEntity(PersonGroupDTO personGroupDTO);

    default PersonGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonGroup personGroup = new PersonGroup();
        personGroup.setId(id);
        return personGroup;
    }
}
