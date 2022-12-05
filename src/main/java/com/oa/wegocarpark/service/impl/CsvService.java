package com.oa.wegocarpark.service.impl;

import com.oa.wegocarpark.dto.CarParkInfoCSVData;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvService {
    private final String CSV_FILE_PATH = "hdb-carpark-information/hdb-carpark-information.csv";
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("jsonschema.json");

    @Cacheable("csv")
    public List<CarParkInfoCSVData> readFromCsv() {
        System.out.println("reading from CSV file...");
        List<CarParkInfoCSVData> carParkInfoCSVData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(
                new InputStreamReader(
                        Thread.currentThread()
                                .getContextClassLoader()
                                .getResourceAsStream(CSV_FILE_PATH)
                ))) {
            List<String[]> r = reader.readAll();
            carParkInfoCSVData = r.stream().parallel().map(cells ->
                    new CarParkInfoCSVData(
                            cells[0],
                            cells[1],
                            cells[2],
                            cells[3],
                            cells[4],
                            cells[5],
                            cells[6],
                            cells[7],
                            cells[8],
                            cells[9],
                            cells[10],
                            cells[11]
                    )).collect(Collectors.toList());
            return carParkInfoCSVData;
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
