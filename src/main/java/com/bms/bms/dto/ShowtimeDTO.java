package com.bms.bms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private Long id;
    private String startTime;
    private Long screenId;
    private Float price;


}
