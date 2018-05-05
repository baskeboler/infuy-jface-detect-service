package uy.com.infuy.jfacedetect.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Face entity.
 */
public class FaceDTO implements Serializable {

    private Long id;

    private String persistedFaceId;

    private Long personGroupPersonId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public Long getPersonGroupPersonId() {
        return personGroupPersonId;
    }

    public void setPersonGroupPersonId(Long personGroupPersonId) {
        this.personGroupPersonId = personGroupPersonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FaceDTO faceDTO = (FaceDTO) o;
        if(faceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), faceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FaceDTO{" +
            "id=" + getId() +
            ", persistedFaceId='" + getPersistedFaceId() + "'" +
            "}";
    }
}
