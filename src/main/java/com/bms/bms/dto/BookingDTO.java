package com.bms.bms.dto;

import com.bms.bms.annotations.RelationField;
import com.bms.bms.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    private Long id;
    private Long userId;
    private String theatreName;
    private Long showtimeId;
    private Float price;
    private Long screenId;
    private String screenName;
    private Long movieId;
    private String movieName;
//    private LocalDateTime bookingTime;
    private LocalDateTime showTime;
    @RelationField("bookedSeats")
    private List<Integer> bookedSeats;

}