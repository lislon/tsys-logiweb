/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CargoDTO {
    private Integer id;
    private String name;
    private String title;
    private int weight;

    public CargoDTO(String name, String title, int weight) {
        this.name = name;
        this.title = title;
        this.weight = weight;
    }
}
