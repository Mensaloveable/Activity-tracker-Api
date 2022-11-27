package com.loveable.tracker.model;


import com.loveable.tracker.enums.Gender;
import com.loveable.tracker.utils.UuidGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "User")
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_uuid_unique",
                        columnNames = {"email", "uuid"})
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(
            name = "id",
            nullable = false,
            updatable = false
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;
    @Column(
            name = "gender",
            nullable = false
    )
    private Gender gender;
    @Column(
            name = "email",
            nullable = false
    )
    private String email;
    @Column(
            name = "password",
            nullable = false
    )
    private String password;
    @Column(
            name = "uuid",
            nullable = false
    )
    private String uuid;

    @PrePersist
    public void setUuid() {
        this.uuid = UuidGenerator.uuid();
    }

    @OneToMany(
            mappedBy = "user",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = LAZY
    )
    private List<Task> tasks;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
