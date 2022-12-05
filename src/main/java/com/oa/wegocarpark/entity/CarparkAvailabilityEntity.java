package com.oa.wegocarpark.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carpark_availability")
public class CarparkAvailabilityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String timestamp;
    @OneToMany(orphanRemoval = true, mappedBy = "carparkAvailability", cascade = CascadeType.ALL)
    private List<CarParkDataEntity> carparkData = new ArrayList<>();
}
