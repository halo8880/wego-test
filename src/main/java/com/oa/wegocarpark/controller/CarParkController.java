package com.oa.wegocarpark.controller;

import com.oa.wegocarpark.dto.CarPark;
import com.oa.wegocarpark.service.CarParkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carparks")
public class CarParkController {
    CarParkService carParkService;

    public CarParkController(CarParkService carParkService) {
        this.carParkService = carParkService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<List<CarPark>> getNearestCarPark(
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "per_page", defaultValue = "0", required = false) int perPage
    ) {

        List<CarPark> nearestCarParks = carParkService.getNearestCarParks(Double.parseDouble(latitude), Double.parseDouble(longitude), page, perPage);
        return ResponseEntity.ok(nearestCarParks);
    }

    @PatchMapping("/batch-availability")
    public ResponseEntity getAvailabilities() throws Exception {
        carParkService.updateCarParkAvailability();
        return ResponseEntity.ok().build();
    }
}
