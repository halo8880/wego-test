package com.oa.wegocarpark.repository;

import com.oa.wegocarpark.entity.CarparkAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarParkAvailabilityRepository extends JpaRepository<CarparkAvailabilityEntity, Long> {
}
