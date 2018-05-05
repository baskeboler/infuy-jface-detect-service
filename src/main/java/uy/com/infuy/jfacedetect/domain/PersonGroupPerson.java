package uy.com.infuy.jfacedetect.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A PersonGroupPerson.
 */
@Entity
@Table(name = "person_group_person")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonGroupPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private PersonGroup personGroup;

    @OneToMany(mappedBy = "personGroupPerson")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Face> faces = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public PersonGroupPerson personId(String personId) {
        this.personId = personId;
        return this;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public PersonGroupPerson name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PersonGroupPerson description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PersonGroup getPersonGroup() {
        return personGroup;
    }

    public PersonGroupPerson personGroup(PersonGroup personGroup) {
        this.personGroup = personGroup;
        return this;
    }

    public void setPersonGroup(PersonGroup personGroup) {
        this.personGroup = personGroup;
    }

    public Set<Face> getFaces() {
        return faces;
    }

    public PersonGroupPerson faces(Set<Face> faces) {
        this.faces = faces;
        return this;
    }

    public PersonGroupPerson addFace(Face face) {
        this.faces.add(face);
        face.setPersonGroupPerson(this);
        return this;
    }

    public PersonGroupPerson removeFace(Face face) {
        this.faces.remove(face);
        face.setPersonGroupPerson(null);
        return this;
    }

    public void setFaces(Set<Face> faces) {
        this.faces = faces;
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
        PersonGroupPerson personGroupPerson = (PersonGroupPerson) o;
        if (personGroupPerson.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personGroupPerson.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonGroupPerson{" +
            "id=" + getId() +
            ", personId='" + getPersonId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
