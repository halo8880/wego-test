package com.oa.wegocarpark.repository;

import com.oa.wegocarpark.entity.CarParkInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkInfoRepository extends JpaRepository<CarParkInfoEntity, Long> {
}
