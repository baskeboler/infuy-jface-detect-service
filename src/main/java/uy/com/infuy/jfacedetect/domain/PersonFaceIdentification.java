package uy.com.infuy.jfacedetect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonFaceIdentification.
 */
@Entity
@Table(name = "person_face_identification")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonFaceIdentification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_person")
    private Long idPerson;

    @Column(name = "id_face_detection")
    private Long idFaceDetection;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public PersonFaceIdentification idPerson(Long idPerson) {
        this.idPerson = idPerson;
        return this;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Long getIdFaceDetection() {
        return idFaceDetection;
    }

    public PersonFaceIdentification idFaceDetection(Long idFaceDetection) {
        this.idFaceDetection = idFaceDetection;
        return this;
    }

    public void setIdFaceDetection(Long idFaceDetection) {
        this.idFaceDetection = idFaceDetection;
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
        PersonFaceIdentification personFaceIdentification = (PersonFaceIdentification) o;
        if (personFaceIdentification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personFaceIdentification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonFaceIdentification{" +
            "id=" + getId() +
            ", idPerson=" + getIdPerson() +
            ", idFaceDetection=" + getIdFaceDetection() +
            "}";
    }
}
