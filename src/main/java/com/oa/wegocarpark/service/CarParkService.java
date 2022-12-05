package com.oa.wegocarpark.service;

import com.oa.wegocarpark.client.dto.CarParkAvailabilityResponse;
import com.oa.wegocarpark.dto.CarPark;

import java.util.List;

public interface CarParkService {
    List<CarPark> getNearestCarParks(Double latitude, Double longtitude, int page, int perPage);

    CarParkAvailabilityResponse updateCarParkAvailability();
}
