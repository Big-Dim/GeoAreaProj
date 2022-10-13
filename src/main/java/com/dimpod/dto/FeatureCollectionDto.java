package com.dimpod.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeatureCollectionDto {
    private String type;
    private Long name;
    private CrsDto crs;
    private List<FeatureDto> features;
    //private List<PropertyDto> propertiesCrs;
}
