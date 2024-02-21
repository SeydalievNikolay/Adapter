package org.seydaliev.controller;

import org.seydaliev.model.MsgA;
import org.seydaliev.model.MsgB;
import org.seydaliev.service.WeatherService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdapterController {
    private final WeatherService weatherService;

    public AdapterController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/adapter")
    public MsgB handleMsgA(@RequestBody MsgA msgA) {
        if (msgA.getIng().equals("ru") && msgA.getCoordinates() != null) {
            String weatherData = weatherService.getWeatherData(msgA.getCoordinates().getLatitude(), msgA.getCoordinates().getLongitude());
            MsgB msgB = new MsgB(msgA.getMsg(), weatherData);
            return msgB;
        } else {
            return null;
        }
    }
}
