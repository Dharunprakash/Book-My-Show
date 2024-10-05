package com.bms.bms.dto;

import com.bms.bms.model.Showtime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private Long id;
    private Date startTime;
    private Long screenId;
    private Float price;

    public static ShowtimeDTO from(Showtime showtime) {
        return ShowtimeDTO.builder()
                .id(showtime.getId())
                .startTime(showtime.getStartTime())
                .screenId(showtime.getScreenId())
                .price(showtime.getPrice())
                .build();
    }

    public Showtime toShowtime() {
        return Showtime.builder()
                .id(id)
                .startTime(startTime)
                .screenId(screenId)
                .price(price)
                .build();
    }
}
