package com.bms.bms.dto;

import com.bms.bms.model.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {
    private Long id;
    private Long screenId;
    private Integer seatNumber;

    public Seat toSeat(){
        return Seat.builder()
                .id(id)
                .screenId(screenId)
                .seatNumber(seatNumber)
                .build();
    }

    public static SeatDTO from(Seat seat){
        return SeatDTO.builder()
                .id(seat.getId())
                .screenId(seat.getScreenId())
                .seatNumber(seat.getSeatNumber())
                .build();
    }
}
