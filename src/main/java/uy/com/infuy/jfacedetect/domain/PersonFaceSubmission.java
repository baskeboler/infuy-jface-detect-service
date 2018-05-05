package uy.com.infuy.jfacedetect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFaceSubmission.
 */
@Entity
@Table(name = "person_face_submission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFaceSubmission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "persisted_face_id")
    private String persistedFaceId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "face_detection_id")
    private Long faceDetectionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public PersonFaceSubmission persistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
        return this;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public Long getPersonId() {
        return personId;
    }

    public PersonFaceSubmission personId(Long personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getFaceDetectionId() {
        return faceDetectionId;
    }

    public PersonFaceSubmission faceDetectionId(Long faceDetectionId) {
        this.faceDetectionId = faceDetectionId;
        return this;
    }

    public void setFaceDetectionId(Long faceDetectionId) {
        this.faceDetectionId = faceDetectionId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonFaceSubmission personFaceSubmission = (PersonFaceSubmission) o;
        if (personFaceSubmission.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personFaceSubmission.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonFaceSubmission{" +
            "id=" + getId() +
            ", persistedFaceId='" + getPersistedFaceId() + "'" +
            ", personId=" + getPersonId() +
            ", faceDetectionId=" + getFaceDetectionId() +
            "}";
    }
}
