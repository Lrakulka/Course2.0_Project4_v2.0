package com.course.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "email"})
@ToString(of = {"email", "firstName", "lastName", "active"})
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "email", unique = true, nullable = false, length = 45)
    private String email;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserRole> userRoles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Bill> bills;
}
