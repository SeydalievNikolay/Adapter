package org.seydaliev.controllerIT;

import org.junit.jupiter.api.Test;
import org.seydaliev.model.MsgA;
import org.seydaliev.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceAControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void testSendMsgA_ValidRequest() throws Exception {
        MsgA.Coordinates coordinates = new MsgA.Coordinates("50.0", "30.0");
        MsgA msgA = new MsgA();
        msgA.setMsg("Hello");
        msgA.setIng("ru");
        msgA.setCoordinates(coordinates);
        String weatherData = "Sunny";
        when(weatherService.getWeatherData(coordinates.getLatitude(), coordinates.getLongitude())).thenReturn(weatherData);

        mockMvc.perform(MockMvcRequestBuilders.post("/serviceA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"msg\":\"Hello\",\"ing\":\"ru\",\"coordinates\":{\"latitude\":\"50.0\",\"longitude\":\"30.0\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(msgA.getMsg()))
                .andExpect(jsonPath("$.weatherData").value(weatherData));
    }

    @Test
    public void testSendMsgA_InvalidRequest() throws Exception {
        // Arrange
        MsgA.Coordinates coordinates = new MsgA.Coordinates("50.0", "30.0");
        MsgA msgA = new MsgA();
        msgA.setMsg("Hello");
        msgA.setIng("en");
        msgA.setCoordinates(coordinates);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/serviceA")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"msg\":\"Hello\",\"ing\":\"en\",\"coordinates\":{\"latitude\":\"50.0\",\"longitude\":\"30.0\"}}"))
                .andExpect(status().isBadRequest());
    }
}
