package com.epam.model;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

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
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "UNSIGNED INT(10)")
    private Integer id;

    @Column(name = "active", nullable = false)
    @Type(type = "yes_no")
    private Boolean active;

    @Column(name = "deleted", nullable = false)
    @Type(type = "yes_no")
    private Boolean deleted;

    @Column(name = "score", nullable = false)
    private Double score;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bill")
    private Set<Card> cards;
}
