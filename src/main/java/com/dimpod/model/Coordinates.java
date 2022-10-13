package com.dimpod.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String x;
    String y;

    @ManyToOne
    @JoinColumn(name="feature_id")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    private Feature feature;
    public Coordinates(String x, String y, Feature feature){
        this.x = x;
        this.y = y;
        this.feature = feature;
    }

}
