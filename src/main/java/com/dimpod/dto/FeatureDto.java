package com.dimpod.dto;
 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FeatureDto implements Serializable {
     private String type;
     private Set<PropertyDto> propDtoList;
     private GeometryDto geometry;
}
