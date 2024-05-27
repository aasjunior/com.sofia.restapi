package com.sofia.backend.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofia.backend.config.exceptions.patient.PatientNotFoundException;
import com.sofia.backend.domain.model.common.enums.Gender;
import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.qchat.QChat;
import com.sofia.backend.domain.model.qchat.QChatRequest;
import com.sofia.backend.domain.model.qchat.QChatResponse;
import com.sofia.backend.domain.repository.PatientRepository;
import com.sofia.backend.domain.repository.QChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QChatService {
    private final QChatRepository qchatRepository;
    private final NeuralNetworkService neuralNetworkService;
    private final PatientRepository patientRepository;

    public ResponseEntity<?> submitQChat(QChatRequest request){
        try{
            ResponseEntity<?> responseEntity = neuralNetworkTest(request.patientId(), request.questions());
            if(responseEntity.getStatusCode() == HttpStatus.OK){
                QChatResponse response = (QChatResponse) responseEntity.getBody();
                QChat qChat = QChat.fromRequest(request, response);
                QChat saved = qchatRepository.save(qChat);
                return new ResponseEntity<>(saved, HttpStatus.CREATED);
            }else{
                throw new Exception("Error generating response: " + responseEntity.getBody());
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<?> neuralNetworkTest(String patientId, Map<String, Boolean> questions){
        try{
            Optional<Patient> patientData = patientRepository.findById(patientId);
            if(patientData.isPresent()){
                String response = generateResponse(patientData.get(), questions);
                neuralNetworkService.postData(response);

                ResponseEntity<String> dataResponse = neuralNetworkService.getData();
                QChatResponse qchatResponse = createQChatResponse(dataResponse.getBody());
                return new ResponseEntity<>(qchatResponse, HttpStatus.OK);
            }else{
                throw new PatientNotFoundException("Patient not found!");
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private QChatResponse createQChatResponse(String body) throws Exception {
        // Parse the JSON body to extract the "resultado" value
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(body);
        String resultado = rootNode.path("resultado").asText();

        if(resultado == null || (!resultado.equals("0") && !resultado.equals("1"))) {
            throw new Exception("Resposta inválida do serviço de rede neural");
        }
        boolean prediction = "1".equals(resultado);
        return new QChatResponse(0.98, prediction);
    }

    private String generateResponse(Patient patient, Map<String, Boolean> questions){
        String sex = patient.getGender() == Gender.Female ? "f" : "m";
        String familyCases = patient.getFamilyCases() ? "yes" : "no";
        String premature = patient.getPremature() ? "yes" : "no";
        String answers = questions.entrySet().stream()
                .map(entry ->
                        String.format("\"%s\": %d", entry.getKey(), entry.getValue() ? 1 : 0))
                .collect(
                        Collectors.joining(",\n")
                );

        return String.format("""
                {
                  "responses": [
                    {
                      "Case_No": 1,
                      %s,
                      "Age_Mons": %d,
                      "Sex": "%s",
                      "Ethnicity": "middle eastern",
                      "Jaundice": "%s",
                      "Family_mem_with_ASD": "%s",
                      "Who_completed_the_test": "family member",
                      "Class_ASD_Traits": ""
                    }
                                
                  ]
                }
                                
                """, answers, patient.getAgeMonths(), sex, premature, familyCases);
    }
}
