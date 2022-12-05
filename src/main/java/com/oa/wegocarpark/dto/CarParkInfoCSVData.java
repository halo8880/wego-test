package com.oa.wegocarpark.dto;

import lombok.Data;

@Data
/**
 * the fieldName in this class should be replaced by camelCase style for consistence with normal style
 */
public class CarParkInfoCSVData {
    private String car_park_no;
    private String address;
    private String x_coord;
    private String y_coord;
    private String car_park_type;
    private String type_of_parking_system;
    private String short_term_parking;
    private String free_parking;
    private String night_parking;
    private String car_park_decks;
    private String gantry_height;
    private String car_park_basement;

    public CarParkInfoCSVData() {
    }

    public CarParkInfoCSVData(String car_park_no, String address, String x_coord, String y_coord, String car_park_type, String type_of_parking_system, String short_term_parking, String free_parking, String night_parking, String car_park_decks, String gantry_height, String car_park_basement) {
        this.car_park_no = car_park_no;
        this.address = address;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.car_park_type = car_park_type;
        this.type_of_parking_system = type_of_parking_system;
        this.short_term_parking = short_term_parking;
        this.free_parking = free_parking;
        this.night_parking = night_parking;
        this.car_park_decks = car_park_decks;
        this.gantry_height = gantry_height;
        this.car_park_basement = car_park_basement;
    }
}
