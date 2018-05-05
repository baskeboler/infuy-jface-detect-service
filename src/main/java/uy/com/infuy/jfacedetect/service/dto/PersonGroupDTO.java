package uy.com.infuy.jfacedetect.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonGroup entity.
 */
public class PersonGroupDTO implements Serializable {

    private Long id;

    private String personGroupId;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonGroupId() {
        return personGroupId;
    }

    public void setPersonGroupId(String personGroupId) {
        this.personGroupId = personGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonGroupDTO personGroupDTO = (PersonGroupDTO) o;
        if(personGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonGroupDTO{" +
            "id=" + getId() +
            ", personGroupId='" + getPersonGroupId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
