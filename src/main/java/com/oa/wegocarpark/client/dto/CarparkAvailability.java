package com.oa.wegocarpark.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarparkAvailability {
    private String timestamp;
    @JsonProperty("carpark_data")
    private List<CarParkData> carparkData = new ArrayList<>();
}
