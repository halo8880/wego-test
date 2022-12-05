package com.oa.wegocarpark.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarPark {
    private String address;
    private String latitude;
    private String longitude;
    @JsonProperty("total_lots")
    private int totalLots;
    @JsonProperty("available_lots")
    private int availableLots;
}
