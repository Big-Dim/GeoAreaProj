package com.dimpod.model;


import com.dimpod.dto.FeatureCollectionDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
 
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class FeatureCollection implements Serializable {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Long name;

    private String CrsType;

    private String CrsName;


    public FeatureCollection(FeatureCollectionDto dto) {
        this.name = dto.getName();
        this.type = dto.getType();
    }

}
