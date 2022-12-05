package com.oa.wegocarpark.service.impl;

import com.oa.wegocarpark.client.CarParkClient;
import com.oa.wegocarpark.client.dto.CarParkAvailabilityResponse;
import com.oa.wegocarpark.client.dto.CarParkInfo;
import com.oa.wegocarpark.client.dto.CarparkAvailability;
import com.oa.wegocarpark.dto.CarPark;
import com.oa.wegocarpark.dto.CarParkInfoCSVData;
import com.oa.wegocarpark.entity.CarParkDataEntity;
import com.oa.wegocarpark.entity.CarParkInfoEntity;
import com.oa.wegocarpark.entity.CarparkAvailabilityEntity;
import com.oa.wegocarpark.repository.CarParkAvailabilityRepository;
import com.oa.wegocarpark.service.CarParkService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarParkServiceImpl implements CarParkService {
    private final CarParkClient carParkClient;
    private final CsvService csvService;
    private final CarParkAvailabilityRepository carParkAvailabilityRepository;

    public CarParkServiceImpl(
            CarParkClient carParkClient,
            CsvService csvService,
            CarParkAvailabilityRepository carParkAvailabilityRepository
    ) {
        this.carParkClient = carParkClient;
        this.csvService = csvService;
        this.carParkAvailabilityRepository = carParkAvailabilityRepository;
    }

    @Override
    public List<CarPark> getNearestCarParks(Double latitude,
                                            Double longtitude, int page, int perPage) {
        Map<String, CarParkInfo> parkingIdToCarParkInfoMap = createParkingIdToCarParkInfoMap();
        List<CarParkInfoCSVData> carParkInfoCSVData = csvService.readFromCsv();
        return carParkInfoCSVData.stream()
                .filter(carParkInfo -> parkingIdToCarParkInfoMap.containsKey(carParkInfo.getCar_park_no()))
                .sorted((o1, o2) ->
                        Double.compare(
                                calcuateDistance(Double.parseDouble(o1.getY_coord()), Double.parseDouble(o1.getX_coord()), latitude, longtitude),
                                calcuateDistance(Double.parseDouble(o2.getY_coord()), Double.parseDouble(o2.getX_coord()), latitude, longtitude)
                        )
                ).skip(page <= 0 ? 0 : (long) (page - 1) * perPage).limit(perPage <= 0 ? carParkInfoCSVData.size() : perPage)
                .map(carParkInfo ->
                        carParkInfoToCarParkResponse(parkingIdToCarParkInfoMap, carParkInfo)).collect(Collectors.toList());
    }

    private static CarPark carParkInfoToCarParkResponse(Map<String, CarParkInfo> parkingIdToCarParkInfoMap, CarParkInfoCSVData carParkInfo) {
        CarParkInfo realtimeCarParkInfo = parkingIdToCarParkInfoMap.get(carParkInfo.getCar_park_no());

        CarPark carPark = new CarPark();
        carPark.setAddress(carParkInfo.getAddress());
        carPark.setLatitude(carParkInfo.getY_coord());
        carPark.setLongitude(carParkInfo.getX_coord());
        carPark.setAvailableLots(realtimeCarParkInfo.getLotsAvailable());
        carPark.setTotalLots(realtimeCarParkInfo.getTotalLots());
        return carPark;
    }

    private Map<String, CarParkInfo> createParkingIdToCarParkInfoMap() {
        Map<String, CarParkInfo> stringCarParkInfoMap = new HashMap<>();
        List<CarparkAvailability> carParkAvailability = updateCarParkAvailability().getItems();
        carParkAvailability.forEach(carparkAvailability -> {
            carparkAvailability.getCarparkData().stream().parallel()
                    .filter(carParkData -> carParkData.getCarparkInfo().get(0).getLotsAvailable() > 0)
                    .forEach(carParkData -> {
                        stringCarParkInfoMap.put(carParkData.getCarparkNumber(), carParkData.getCarparkInfo().get(0));
                    });
        });
        return stringCarParkInfoMap;
    }

    private Double calcuateDistance(
            Double latitude1,
            Double longtitude1,
            Double latitude2,
            Double longtitude2) {
        return Math.sqrt(Math.pow(Math.abs(latitude1 - latitude2), 2d) + Math.pow(Math.abs(longtitude1 - longtitude2), 2d));
    }


    @Override
    public CarParkAvailabilityResponse updateCarParkAvailability() {
        CarParkAvailabilityResponse carParkAvailabilityResponse = carParkClient.carParkAvailability(LocalDateTime.now());
        List<CarparkAvailabilityEntity> entities = carParkAvailabilityResponseToEntity(carParkAvailabilityResponse);
        /**
         * consider async saving data to storage here
         */
        carParkAvailabilityRepository.saveAll(entities);
        /**
         * convert entities to DTO and return instead
         */
        return carParkAvailabilityResponse;
    }

    private List<CarparkAvailabilityEntity> carParkAvailabilityResponseToEntity(CarParkAvailabilityResponse carParkAvailabilityResponse) {
        return carParkAvailabilityResponse.getItems().stream().parallel()
                .map(dto -> {
                    CarparkAvailabilityEntity carparkAvailabilityEntity = new CarparkAvailabilityEntity();
                    carparkAvailabilityEntity.setTimestamp(dto.getTimestamp());
                    List<CarParkDataEntity> carParkDataEntities = dto.getCarparkData().stream().parallel()
                            .map(carParkData -> {
                                CarParkDataEntity carParkDataEntity = new CarParkDataEntity();
                                carParkDataEntity.setCarparkNumber(carParkData.getCarparkNumber());
                                List<CarParkInfoEntity> carParkInfoEntities = carParkData.getCarparkInfo().stream().parallel()
                                        .map(carParkInfo -> {
                                            CarParkInfoEntity carParkInfoEntity = new CarParkInfoEntity();
                                            carParkInfoEntity.setLotsAvailable(carParkInfo.getLotsAvailable());
                                            carParkInfoEntity.setTotalLots(carParkInfo.getTotalLots());
                                            carParkInfoEntity.setLotType(carParkInfo.getLotType());
                                            carParkInfoEntity.setCarParkData(carParkDataEntity);
                                            return carParkInfoEntity;
                                        }).collect(Collectors.toList());
                                carParkDataEntity.setCarparkInfo(carParkInfoEntities);
                                carParkDataEntity.setCarparkAvailability(carparkAvailabilityEntity);
                                return carParkDataEntity;
                            }).collect(Collectors.toList());
                    carparkAvailabilityEntity.setCarparkData(carParkDataEntities);
                    return carparkAvailabilityEntity;
                }).collect(Collectors.toList());
    }
}
