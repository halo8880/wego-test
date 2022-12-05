package com.oa.wegocarpark.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarParkData {
    @JsonProperty("carpark_info")
    private List<CarParkInfo> carparkInfo = new ArrayList<>();

    @JsonProperty("carpark_number")
    private String carparkNumber;
}
