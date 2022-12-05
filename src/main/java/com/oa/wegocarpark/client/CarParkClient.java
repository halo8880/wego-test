package com.oa.wegocarpark.client;

import com.oa.wegocarpark.client.dto.CarParkAvailabilityResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CarParkClient {
    public final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    private final RestTemplate restTemplate;

    public CarParkClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .defaultHeader("accept", "application/json")
                .build();
    }

    public CarParkAvailabilityResponse carParkAvailability(LocalDateTime dateTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String requestUrl = "https://api.data.gov.sg/v1/transport/carpark-availability?date_time=" + dateTime.format(dtf);
        return restTemplate.getForObject(requestUrl, CarParkAvailabilityResponse.class);
    }
}
