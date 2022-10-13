package com.dimpod.dto;

import com.dimpod.model.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@ToString

public class GeometryDto {
    private String type;
    private List<List<List<List<String>>>> coordinates;

    public List<List<String>> getCoordinates() {
        if(coordinates != null && coordinates.size() >0){
            return coordinates.get(0).get(0);
        }
        return null;
    }

}
