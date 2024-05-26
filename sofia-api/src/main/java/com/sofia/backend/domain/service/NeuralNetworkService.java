package com.sofia.backend.domain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NeuralNetworkService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("consumer.api-flask.respostas.url")
    private String dataPostUrl;

    @Value("consumer.api-flask.resultado.url")
    private String dataGetUrl;

    public ResponseEntity<String> postData(String jsonData) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
            return restTemplate.postForEntity(dataPostUrl, request, String.class);
        }catch(RestClientException e){
            throw new RuntimeException("Falha ao postar dados", e);
        }
    }

    public ResponseEntity<String> getData() {
        try{
            return restTemplate.exchange(dataGetUrl, HttpMethod.GET, null, String.class);
        }catch(RestClientException e){
            throw new RuntimeException("Falha ao obter dados", e);
        }
    }
}
