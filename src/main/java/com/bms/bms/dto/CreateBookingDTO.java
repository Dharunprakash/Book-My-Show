package com.bms.bms.dto;

import com.bms.bms.annotations.RelationField;
import com.bms.bms.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDTO {
    private Long userId;
    private Long showtimeId;
    @RelationField("seats")
    private List<Long> seatIds;

    public Booking toBooking(){
        return Booking.builder()
                .userId(userId)
                .showtimeId(showtimeId)
                .build();
    }

    public static CreateBookingDTO from(Booking booking){
        return CreateBookingDTO.builder()
                .userId(booking.getUserId())
                .showtimeId(booking.getShowtimeId())
                .build();
    }
}
