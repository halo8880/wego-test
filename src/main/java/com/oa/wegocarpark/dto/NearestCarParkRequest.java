package com.oa.wegocarpark.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearestCarParkRequest {
    private String latitude;
    private String longitude;
    private int page;
    private int perPage;
}
