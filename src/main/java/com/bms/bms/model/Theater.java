package com.bms.bms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Theater {
    private Long id;
    private String name;
    private String location;
    private String address;
    private List<Screen> screens;
}
