package br.com.wine.winechallenge.consumer.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Consumer {
    private RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<Object> getHttp(String url) {
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
        return response;
    }

}
