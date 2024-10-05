package com.bms.bms.dto;

import com.bms.bms.model.Screen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScreenDTO {
    private Long id;
    private String name;
    private Long theaterId;

    public Screen toScreen(){
        return Screen.builder()
                .id(id)
                .name(name)
                .theaterId(theaterId)
                .build();
    }

    public static ScreenDTO fromScreen(Screen screen){
        return ScreenDTO.builder()
                .id(screen.getId())
                .name(screen.getName())
                .theaterId(screen.getTheaterId())
                .build();
    }
}
