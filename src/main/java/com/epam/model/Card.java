package com.epam.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "card")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"name", "active", "deleted"})
public class Card {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active;

    @Column(name = "deleted", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "id_bill", columnDefinition = "INT UNSIGNED", nullable = false)
    private Bill bill;
}
