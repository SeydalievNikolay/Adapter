package org.seydaliev.service;

import org.seydaliev.model.MsgA;
import org.seydaliev.model.MsgB;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ServiceA {
    private final RestTemplate restTemplate;

    public ServiceA(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void sendMsgToServiceB(MsgA msgA) {
        String serviceBUrl = "http://localhost:8081/serviceB";
        restTemplate.postForEntity(serviceBUrl, msgA, MsgB.class);
    }
}
