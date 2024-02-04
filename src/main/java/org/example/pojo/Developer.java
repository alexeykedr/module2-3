package org.example.pojo;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "first_name")
    private String firstName;

    @Column (name = "last_name")
    private String lastName;

    @OneToMany
    @JoinColumn (name = "developer_id")
    private List<Skill> skills;

    @OneToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column (name = "status")
    private Status status;

    public Developer() {
    }

    public Developer(Long id, String firstName, String lastName, List<Skill> skills, Specialty specialty, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.skills = skills;
        this.specialty = specialty;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Developer developer)) return false;
        return Objects.equals(getId(), developer.getId()) &&
                Objects.equals(getFirstName(), developer.getFirstName()) &&
                Objects.equals(getLastName(), developer.getLastName()) &&
                Objects.equals(getSkills(), developer.getSkills()) &&
                Objects.equals(getSpecialty(), developer.getSpecialty()) &&
                getStatus() == developer.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getSkills(), getSpecialty(), getStatus());
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skills=" + skills +
                ", specialty=" + specialty +
                ", status=" + status +
                '}';
    }

    public boolean isNew() {
        return getId() == null;
    }
}
