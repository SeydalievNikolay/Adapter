package org.seydaliev.controller;

import org.seydaliev.model.MsgA;
import org.seydaliev.model.MsgB;
import org.seydaliev.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ServiceAController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService;

    @PostMapping("/serviceA")
    public ResponseEntity<MsgB> sendMsgA(@RequestBody MsgA msgA) {

        if (msgA.getIng().equals("ru") && msgA.getCoordinates() != null) {
            String weatherData = weatherService.getWeatherData(msgA.getCoordinates().getLatitude(), msgA.getCoordinates().getLongitude());
            MsgB msgB = new MsgB(msgA.getMsg(), weatherData);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<MsgB> requestEntity = new HttpEntity<>(msgB, headers);
            String serviceBUrl = "http://localhost:8081/serviceB";
            ResponseEntity<MsgB> response = restTemplate.exchange(
                    serviceBUrl,
                    HttpMethod.POST,
                    requestEntity,
                    MsgB.class);
            return ResponseEntity.ok(response.getBody());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
