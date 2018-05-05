package uy.com.infuy.jfacedetect.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the PersonGroupPerson entity.
 */
public class PersonGroupPersonDTO implements Serializable {

    private Long id;

    private String personId;

    private String name;

    private String description;

    private Long personGroupId;

    private String personGroupAzureId;

    public String getPersonGroupAzureId() {
        return personGroupAzureId;
    }

    public void setPersonGroupAzureId(String personGroupAzureId) {
        this.personGroupAzureId = personGroupAzureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public Long getPersonGroupId() {
        return personGroupId;
    }

    public void setPersonGroupId(Long personGroupId) {
        this.personGroupId = personGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PersonGroupPersonDTO personGroupPersonDTO = (PersonGroupPersonDTO) o;
        if(personGroupPersonDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personGroupPersonDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonGroupPersonDTO{" +
            "id=" + id +
            ", personId='" + personId + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", personGroupId=" + personGroupId +
            ", personGroupAzureId='" + personGroupAzureId + '\'' +
            '}';
    }
}
