package com.epam.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "name"})
@ToString(of = {"name", "active", "deleted"})
public class Card {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "UNSIGNED INT(10)")
    private Integer id;

    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "active", nullable = false)
    @Type(type = "yes_no")
    private Boolean active;

    @Column(name = "deleted", nullable = false)
    @Type(type = "yes_no")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "id_bill")
    private Bill bill;
}
