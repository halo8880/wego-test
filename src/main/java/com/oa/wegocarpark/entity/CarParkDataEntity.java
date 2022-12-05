package com.oa.wegocarpark.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "carpark_data",
        uniqueConstraints = @UniqueConstraint(name = "carpark_number_unique", columnNames = {"carpark_number", "id"}))
public class CarParkDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "carpark_number")
    private String carparkNumber;

    @OneToMany(mappedBy = "carParkData", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarParkInfoEntity> carparkInfo = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "carpark_availability_id")
    private CarparkAvailabilityEntity carparkAvailability;

}
