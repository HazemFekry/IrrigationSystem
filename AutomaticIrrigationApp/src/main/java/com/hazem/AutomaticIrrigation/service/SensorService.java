package com.hazem.AutomaticIrrigation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazem.AutomaticIrrigation.entity.TimeSlotStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SensorService {

    @Autowired
    TimeSlotService timeSlotService;
    @Value("${sensor.url}")
    private String sensorUrl;

    private long plotId;

    @Retryable(retryFor = IOException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    public void callSensor(long plotNumber, int duration, float amountOfWater, long plotId) throws JsonProcessingException {
        log.info("Calling sensor to irrigate Plot [" + plotId + "] irrigated successfully");
        log.debug(String.format("Calling sensor with following args:{plotNumber: %s, duration: %s, amountOfWater: %s"
                , plotNumber, duration, amountOfWater));
        this.plotId = plotId;
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("callRestServer");
        String url = sensorUrl;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = createResponseBody(plotNumber, duration, amountOfWater);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Plot [" + plotId + "] irrigated successfully");
            } else {
                log.error("Request failed with status code: " + response.getStatusCodeValue());
            }
        } catch (Exception e) {
            log.warn("Couldn't start irrigation for plot [" + plotId + "] retrying in while");
            throw e;
        }


    }

    @Recover
    void recover(Exception e) {
        timeSlotService.updateTimeSlotStatus(plotId, TimeSlotStatus.FAILED);
        log.error("Cannot start irrigation for plot id:" + plotId);
        // here we could call alerting system (send SMS, Send mail, or call alert device)
        throw new RuntimeException("Cannot start irrigation for plot id:" + plotId, e);
    }

    private static String createResponseBody(long plotNumber, int duration, float amountOfWater) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("plotNumber", plotNumber);
        map.put("duration", duration);
        map.put("amountOfWater", amountOfWater);
        return new ObjectMapper().writeValueAsString(map);
    }

    public void callSensor(long plotId) {
        this.plotId = plotId;


    }
}
