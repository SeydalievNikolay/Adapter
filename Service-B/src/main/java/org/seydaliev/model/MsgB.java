package org.seydaliev.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONException;
import org.json.JSONObject;

public class MsgB {
    private String txt;
    private LocalDateTime created;
    private Integer currentTemp;

    public MsgB(String txt, String weatherData) {
        this.txt = txt;
        this.created = LocalDateTime.now();
        this.currentTemp = extractTemperature(weatherData);
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Integer getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(Integer currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getCreatedRfc3339() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return created.format(formatter);
    }

    public void setCreatedRfc3339(String createdRfc3339) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        this.created = LocalDateTime.parse(createdRfc3339, formatter);
    }

    private Integer extractTemperature(String weatherData) {
        try {
            JSONObject jsonObject = new JSONObject(weatherData);
            JSONObject main = jsonObject.getJSONObject("main");
            double temperatureInCelsius = main.getDouble("temp");

            int temperature = (int) Math.round(temperatureInCelsius -  273.15);
            return temperature;
        } catch (JSONException e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            return null;
        }
    }

}
