package com.dimpod.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String key;
    String value;

    @ManyToOne
    private Feature feature;
    public Property(String key, String value, Feature feature) {
        this.key = key;
        this.value = value;
    }

}
