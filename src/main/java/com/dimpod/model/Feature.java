package com.dimpod.model;
 
import com.dimpod.dto.FeatureDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table
public class Feature implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String type;
    private String geometryType;

     @OneToMany(mappedBy= "feature", cascade= CascadeType.ALL)
    private List<Property> properties;

    @OneToMany(mappedBy= "feature", cascade= CascadeType.ALL)
    private List<Coordinates> coordinates;


    public Feature(FeatureDto dto) {
        this.type = dto.getType();

        this.geometryType = dto.getGeometry().getType();
        List<List<String>> coord = dto.getGeometry().getCoordinates();
        this.coordinates = dto.getGeometry().getCoordinates().stream().map(e->{
            return new Coordinates(e.get(0), e.get(1), this) ;} )
                .collect(Collectors.toList());
        this.properties = dto.getPropDtoList().stream().map(e->{
            return new Property(e.getKey(), e.getValue(), this);
        }).collect(Collectors.toList());
    }
}
