package com.dimpod.dto;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PropertyDto {
    String key;
    String value;
}
