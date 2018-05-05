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
 * A PersonGroup.
 */
@Entity
@Table(name = "person_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "person_group_id")
    private String personGroupId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "personGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonGroupPerson> people = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonGroupId() {
        return personGroupId;
    }

    public PersonGroup personGroupId(String personGroupId) {
        this.personGroupId = personGroupId;
        return this;
    }

    public void setPersonGroupId(String personGroupId) {
        this.personGroupId = personGroupId;
    }

    public String getName() {
        return name;
    }

    public PersonGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public PersonGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<PersonGroupPerson> getPeople() {
        return people;
    }

    public PersonGroup people(Set<PersonGroupPerson> personGroupPeople) {
        this.people = personGroupPeople;
        return this;
    }

    public PersonGroup addPerson(PersonGroupPerson personGroupPerson) {
        this.people.add(personGroupPerson);
        personGroupPerson.setPersonGroup(this);
        return this;
    }

    public PersonGroup removePerson(PersonGroupPerson personGroupPerson) {
        this.people.remove(personGroupPerson);
        personGroupPerson.setPersonGroup(null);
        return this;
    }

    public void setPeople(Set<PersonGroupPerson> personGroupPeople) {
        this.people = personGroupPeople;
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
        PersonGroup personGroup = (PersonGroup) o;
        if (personGroup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personGroup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonGroup{" +
            "id=" + getId() +
            ", personGroupId='" + getPersonGroupId() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
