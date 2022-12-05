package com.oa.wegocarpark.repository;

import com.oa.wegocarpark.entity.CarParkDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkDataRepository extends JpaRepository<CarParkDataEntity, Long> {
}
