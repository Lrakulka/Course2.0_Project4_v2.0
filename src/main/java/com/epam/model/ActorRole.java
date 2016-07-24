package com.epam.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="actor_role")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "role"})
@ToString(of = {"role"})
public class ActorRole {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "UNSIGNED INT(10)")
    private Integer id;

    @Column(name = "role", nullable = false, length = 10)
    private String role;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;
}
