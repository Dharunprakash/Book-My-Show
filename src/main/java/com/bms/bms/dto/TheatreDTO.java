package com.bms.bms.dto;

import com.bms.bms.annotations.JoinMappingId;
import com.bms.bms.model.Theatre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheatreDTO {
    @JoinMappingId("theater_id")
    private Long id;
    @JoinMappingId("theatre_name")
    private String name;
    private String location;
    private String address;

    public Theatre toTheatre() {
        return Theatre.builder()
                .id(id)
                .name(name)
                .location(location)
                .address(address)
                .build();
    }

    public static TheatreDTO fromTheatre(Theatre theatre) {
        return TheatreDTO.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .location(theatre.getLocation())
                .address(theatre.getAddress())
                .build();
    }
}
