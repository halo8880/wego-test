package com.oa.wegocarpark.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "carpark_info")
public class CarParkInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private int totalLots;
    private String lotType;
    private int lotsAvailable;
    @ManyToOne
    @JoinColumn(name = "carpark_data_id")
    private CarParkDataEntity carParkData;
}
