package com.sofia.backend.domain.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NeuralNetworkService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> postData(String jsonData) {
        try{
            String url = "http://localhost:5000/respostas";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
            return restTemplate.postForEntity(url, request, String.class);
        }catch(RestClientException e){
            throw new RuntimeException("Falha ao postar dados", e);
        }
    }

    public ResponseEntity<String> getData() {
        try{
            String url = "http://localhost:5000/resultado";
            return restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        }catch(RestClientException e){
            throw new RuntimeException("Falha ao obter dados", e);
        }
    }
}
