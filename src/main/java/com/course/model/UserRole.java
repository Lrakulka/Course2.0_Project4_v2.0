package com.course.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="user_role")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "role"})
@ToString(of = {"role"})
public class UserRole {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "role", nullable = false, length = 11)
    private String role;

    @JoinColumn(name = "user_id", columnDefinition = "INT UNSIGNED", nullable = false)
    @ManyToOne
    private User user;
}
