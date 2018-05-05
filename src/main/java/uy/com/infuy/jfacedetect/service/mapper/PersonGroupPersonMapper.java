package uy.com.infuy.jfacedetect.service.mapper;

import uy.com.infuy.jfacedetect.domain.*;
import uy.com.infuy.jfacedetect.service.dto.PersonGroupPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PersonGroupPerson and its DTO PersonGroupPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {PersonGroupMapper.class})
public interface PersonGroupPersonMapper extends EntityMapper<PersonGroupPersonDTO, PersonGroupPerson> {

    @Mapping(source = "personGroup.id", target = "personGroupId")
    @Mapping(source = "personGroup.personGroupId", target = "personGroupAzureId")
    PersonGroupPersonDTO toDto(PersonGroupPerson personGroupPerson);

    @Mapping(source = "personGroupId", target = "personGroup")
    @Mapping(target = "faces", ignore = true)
    PersonGroupPerson toEntity(PersonGroupPersonDTO personGroupPersonDTO);

    default PersonGroupPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        PersonGroupPerson personGroupPerson = new PersonGroupPerson();
        personGroupPerson.setId(id);
        return personGroupPerson;
    }
}
