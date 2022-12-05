package com.oa.wegocarpark.client.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarParkAvailabilityResponse {
    private List<CarparkAvailability> items = new ArrayList<>();
}
