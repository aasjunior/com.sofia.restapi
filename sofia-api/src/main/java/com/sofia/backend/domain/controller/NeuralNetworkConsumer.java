package com.sofia.backend.domain.controller;

import com.sofia.backend.config.exceptions.DataUnavailableException;
import com.sofia.backend.domain.service.NeuralNetworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/neural-network")
public class NeuralNetworkConsumer {
    private final NeuralNetworkService neuralNetworkService;

    @PostMapping("/respostas")
    public ResponseEntity<String> postData(@RequestBody String jsonData){
        return neuralNetworkService.postData(jsonData);
    }

    @GetMapping("/resultado")
    public ResponseEntity<String> getData(){
        try{
            return neuralNetworkService.getData();
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
