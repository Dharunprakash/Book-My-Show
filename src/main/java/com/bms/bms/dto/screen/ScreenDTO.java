package com.bms.bms.dto.screen;

import com.bms.bms.model.Screen;
import lombok.*;

@ToString
@Setter
@Getter
public class ScreenDTO extends CreateScreenDTO {
    private Long id;
    private Long theaterId;

    public static ScreenDTO fromScreen(Screen screen){
        var screenDTO = new ScreenDTO();
        screenDTO.setId(screen.getId());
        screenDTO.setName(screen.getName());
        screenDTO.setTheaterId(screen.getTheaterId());
        return screenDTO;
    }

    @Override
    public Screen toScreen(){
        return Screen.builder()
                .id(id)
                .name(getName())
                .theaterId(getTheaterId())
                .build();
    }
}