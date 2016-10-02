/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.api.action.dto;

import lombok.AllArgsConstructor;

/**
 * DTO for returning cities list for autocomplete.
 */
@AllArgsConstructor
public class CityAutocompleteDTO
{
    public int id;
    public String name;
}

