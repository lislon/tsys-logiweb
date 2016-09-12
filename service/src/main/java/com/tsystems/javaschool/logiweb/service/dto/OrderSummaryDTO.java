/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * Created by Igor Avdeev on 9/5/16.
 */
@Data
@Builder
public class OrderSummaryDTO {
    public enum Status {NEW, PREPARED, FINISHED};

    private int id;
    private String truckName;
    private String cityStartName;
    private String cityEndName;
    private int cityStartId;
    private int cityEndId;
    private int truckId;
    private int maxPayload;
    private Date dateCreated;
    private Status status;
    private int routeLength;
}
