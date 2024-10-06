package com.bms.bms.dto.screen;

import com.bms.bms.model.Screen;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CreateScreenDTO {
    private String name;

    public CreateScreenDTO fromUser(Screen screen){
        var createScreenDTO = new CreateScreenDTO();
        createScreenDTO.setName(screen.getName());
        return createScreenDTO;
    }

    public Screen toScreen(){
        return Screen.builder()
                .name(name)
                .build();
    }
}
