package com.sofia.backend.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofia.backend.config.exceptions.checklist.TestNotFoundException;
import com.sofia.backend.config.exceptions.patient.PatientNotFoundException;
import com.sofia.backend.domain.model.checklist.qchat.TestResponse;
import com.sofia.backend.domain.model.common.enums.Checklist;
import com.sofia.backend.domain.model.common.enums.ChecklistType;
import com.sofia.backend.domain.model.common.enums.Gender;
import com.sofia.backend.domain.model.patient.Patient;
import com.sofia.backend.domain.model.checklist.qchat.QChat;
import com.sofia.backend.domain.model.checklist.qchat.QChatRequest;
import com.sofia.backend.domain.model.checklist.qchat.QChatResponse;
import com.sofia.backend.domain.repository.PatientRepository;
import com.sofia.backend.domain.repository.QChatRepository;
import io.swagger.v3.core.util.Json;
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

                Optional<QChat> existQChat = qchatRepository.findByPatientId(request.patientId());
                existQChat.ifPresent(q -> qChat.setId(q.getId()));
                QChat savedQChat = qchatRepository.save(qChat);

                TestResponse testResponse = createTestResponse(savedQChat);
                return new ResponseEntity<>(testResponse, HttpStatus.CREATED);
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
                ResponseEntity<String> postResponse = neuralNetworkService.postData(response);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(postResponse.getBody());
                int qchatId = rootNode.path("qchat_id").asInt();

                ResponseEntity<String> dataResponse = neuralNetworkService.getData(qchatId);
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

    private TestResponse createTestResponse(QChat qChat){
        return new TestResponse(
                qChat.getId(),
                Checklist.QChat,
                ChecklistType.SCREENING,
                qChat.getRegisterDate(),
                qChat.getResponses().result()
        );
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
                      %s,
                      "Age_Mons": %d,
                      "Sex": "%s",
                      "Ethnicity": "black",
                      "Jaundice": "%s",
                      "Family_mem_with_ASD": "%s",
                      "Class_ASD_Traits": ""
                }
                """, answers, patient.getAgeMonths(), sex, premature, familyCases);
    }

    public TestResponse getQChat(String patientId){
        Optional<QChat> qchatData = qchatRepository.findByPatientId(patientId);
        if(qchatData.isPresent()) {
            QChat qchat = qchatData.get();

            return createTestResponse(qchat);
        }else{
            throw new TestNotFoundException("Test not found for patientId: " + patientId);
        }
    }

    public QChat getQChatResponses(String testId){
        Optional<QChat> qchatData = qchatRepository.findById(testId);
        if(qchatData.isPresent()) {
            return qchatData.get();
        }else{
            throw new TestNotFoundException("Test not found for testId: " + testId);
        }
    }
}
