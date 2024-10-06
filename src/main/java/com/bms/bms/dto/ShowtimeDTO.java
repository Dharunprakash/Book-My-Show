package com.bms.bms.dto;

import com.bms.bms.annotations.JoinMappingId;
import com.bms.bms.model.Showtime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    @JoinMappingId("showtime_id")
    private Long id;
    private LocalDateTime startTime;
    private Long screenId;
    private Float price;

    public static ShowtimeDTO fromShowtime(Showtime showtime) {
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
