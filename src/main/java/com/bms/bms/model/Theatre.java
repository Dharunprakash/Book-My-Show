package com.bms.bms.model;

import com.bms.bms.annotations.JoinMappingId;
import com.bms.bms.annotations.RelationField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Theatre {
    @JoinMappingId("theater_id")
    private Long id;
    private String name;
    private String location;
    private String address;
    @RelationField("screens")
    private List<Screen> screens;
}
