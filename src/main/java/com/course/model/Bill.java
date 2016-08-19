package com.course.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="bill")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "score"})
@ToString(of = {"score", "active"})
public class Bill {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 45)
    private String name;

    @Column(name = "score", nullable = false)
    private Double score;

    @Column(name = "active", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean active;

    @Column(name = "deleted", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bill")
    private Set<Card> cards;
}
