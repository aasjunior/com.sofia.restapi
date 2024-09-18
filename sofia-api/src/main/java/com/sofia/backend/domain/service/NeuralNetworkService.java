package com.sofia.backend.domain.service;

import com.sofia.backend.config.exceptions.DataUnavailableException;
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
            String url = "http://127.0.0.1:8000/respostas";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<>(jsonData, headers);
            return restTemplate.postForEntity(url, request, String.class);
        }catch(RestClientException e){
            throw new RuntimeException("Falha ao postar os dados", e);
        }
    }

    public ResponseEntity<String> getData(int id) throws DataUnavailableException {
        try{
            String url = "http://127.0.0.1:8000/resultado/" + id;
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            if (response.getBody() == null) {
                throw new DataUnavailableException("O corpo da resposta está vazio");
            }
            return response;
        }catch(RestClientException e){
            throw new DataUnavailableException("Falha ao obter dados", e);
        }
    }
}
