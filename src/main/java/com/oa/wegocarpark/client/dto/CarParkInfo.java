package com.oa.wegocarpark.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CarParkInfo {
    @JsonProperty("total_lots")
    private int totalLots;
    @JsonProperty("lot_type")
    private String lotType;
    @JsonProperty("lots_available")
    private int lotsAvailable;
}
