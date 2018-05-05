package uy.com.infuy.jfacedetect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Face.
 */
@Entity
@Table(name = "face")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Face implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "persisted_face_id")
    private String persistedFaceId;

    @ManyToOne
    private PersonGroupPerson personGroupPerson;

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

    public Face persistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
        return this;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public PersonGroupPerson getPersonGroupPerson() {
        return personGroupPerson;
    }

    public Face personGroupPerson(PersonGroupPerson personGroupPerson) {
        this.personGroupPerson = personGroupPerson;
        return this;
    }

    public void setPersonGroupPerson(PersonGroupPerson personGroupPerson) {
        this.personGroupPerson = personGroupPerson;
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
        Face face = (Face) o;
        if (face.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), face.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Face{" +
            "id=" + getId() +
            ", persistedFaceId='" + getPersistedFaceId() + "'" +
            "}";
    }
}
