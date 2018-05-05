package uy.com.infuy.jfacedetect.service;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uy.com.infuy.jfacedetect.config.ApplicationProperties;
import uy.com.infuy.jfacedetect.domain.Face;
import uy.com.infuy.jfacedetect.domain.FaceDetection;
import uy.com.infuy.jfacedetect.domain.PersonFaceIdentification;
import uy.com.infuy.jfacedetect.domain.PersonFaceSubmission;
import uy.com.infuy.jfacedetect.domain.PersonGroup;
import uy.com.infuy.jfacedetect.domain.PersonGroupPerson;
import uy.com.infuy.jfacedetect.repository.FaceDetectionRepository;
import uy.com.infuy.jfacedetect.repository.PersonFaceIdentificationRepository;
import uy.com.infuy.jfacedetect.repository.PersonGroupPersonRepository;
import uy.com.infuy.jfacedetect.repository.PersonGroupRepository;
import uy.com.infuy.jfacedetect.service.dto.TrainingStatusDTO;
import uy.com.infuy.jfacedetect.service.errors.AzureFaceApiException;
import uy.com.infuy.jfacedetect.service.errors.AzureNotFoundException;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AzureFaceApiService {

    private static final Logger logger = LoggerFactory.getLogger(AzureFaceApiService.class);
    private static final String API_KEY_HEADER = "Ocp-Apim-Subscription-Key";

    @Autowired
    private PersonGroupPersonRepository personRepository;

    @Autowired
    private FaceDetectionRepository faceDetectionRepository;
    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private PersonGroupRepository groupRepository;
    @Autowired
    private PersonFaceIdentificationRepository identificationRepository;

    @PostConstruct
    public void init() {
        logger.info("Initializing Azure Face API Service");
    }

    public void createPersonGroup(PersonGroup personGroup) throws AzureFaceApiException {
        logger.info("FaceAPI create person group {}", personGroup);
        if (personGroup.getId() != null) {
            throw new AzureFaceApiException("person group already exists");
        }

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        Map<String, String> body = new HashMap<>();
        body.put("name", personGroup.getName());
        body.put("userData", personGroup.getDescription());
        UUID uuid = UUID.randomUUID();

        String url = String.format("%s/personGroups/%s", applicationProperties.getFaceApi().getEndpoint(), uuid.toString());
        logger.info("Sending request to {}", url);

        HttpHeaders headers = getHttpHeaders();
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        try {
            restTemplate.put(url, request);
            personGroup.setPersonGroupId(uuid.toString());
            logger.info("Azure submission successful for {}", personGroup);

        } catch (RestClientException e) {
            throw new AzureFaceApiException(e);
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(API_KEY_HEADER, applicationProperties.getFaceApi().getApiKey());
        return headers;
    }

    public PersonGroupPerson createPerson(PersonGroupPerson person) throws AzureFaceApiException {
        logger.info("Submitting new person to FaceAPI: {}", person);

        if (person.getPersonGroup() == null) {
            throw new AzureFaceApiException("No person group");
        }
        if (person.getId() != null || person.getPersonId() != null) {
            throw new AzureFaceApiException("Person already created");
        }
        if (StringUtils.isEmpty(person.getName())) {
            throw new AzureFaceApiException("No name specified");
        }
        String personGroupId = person.getPersonGroup().getPersonGroupId();
        String url = String.format("%s/personGroups/%s/persons", applicationProperties.getFaceApi().getEndpoint(), personGroupId);
        logger.info("Sending request to {}", url);
        Map<String, String> body = new HashMap<>();
        body.put("name", person.getName());
        body.put("userData", person.getDescription());

        HttpHeaders headers = getHttpHeaders();

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<CreatePersonResponse> entity = restTemplate.exchange(url, HttpMethod.POST, request, CreatePersonResponse.class);
            if (entity.getStatusCode() != HttpStatus.OK) {
                throw new AzureFaceApiException("Failed to create person: " + entity.getBody().toString());
            }
            logger.info("Person created successfully with personId: " + entity.getBody().personId);
            person.setPersonId(entity.getBody().getPersonId());
        } catch (RestClientException e) {
            throw new AzureFaceApiException(e);
        }

        return person;
    }

    void addFace(byte[] buffer, PersonGroupPerson person) throws URISyntaxException {
        logger.info("Submitting new person to FaceAPI: {}", person);

        if (person.getPersonGroup() == null) {
            throw new AzureFaceApiException("No person group");
        }
        if (person.getId() != null || person.getPersonId() != null) {
            throw new AzureFaceApiException("Person already created");
        }
        if (StringUtils.isEmpty(person.getName())) {
            throw new AzureFaceApiException("No name specified");
        }
        String personGroupId = person.getPersonGroup().getPersonGroupId();
        String url = String.format("%s/personGroups/%s/persons", applicationProperties.getFaceApi().getEndpoint(), personGroupId);
        logger.info("Sending request to {}", url);

        HttpHeaders headers = getHttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<byte[]> request = new HttpEntity<>(buffer, headers);
        ResponseEntity<Map> responseEntity = restTemplate
            .exchange(new URI(url), HttpMethod.POST, request, Map.class);
        ;
        //        restTemplate.po
//        RestTemplate
    }

    public Optional<PersonFaceIdentification> identify(Long idFaceDetection, Long idPersonGroup) {
        FaceDetection faceDetection = faceDetectionRepository.findById(idFaceDetection)
            .orElseThrow(AzureNotFoundException::new);
        PersonGroup personGroup = groupRepository.findById(idPersonGroup)
            .orElseThrow(AzureNotFoundException::new);


        return Optional.empty();
    }


    public void trainPersonGroup(Long idPersonGroup) {
        PersonGroup personGroup = groupRepository.findById(idPersonGroup)
            .orElseThrow(AzureNotFoundException::new);

        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/personGroups/%s/train",
            applicationProperties.getFaceApi().getEndpoint(),
            personGroup.getPersonGroupId()
        );
        HttpEntity request = new HttpEntity<Void>(null, getHttpHeaders());
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(url, request, Map.class);
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new AzureFaceApiException(responseEntity.getStatusCode().getReasonPhrase());
        }
    }

    public TrainingStatusDTO getTrainingStatus(Long idPersonGroup) {
        PersonGroup personGroup = groupRepository.findById(idPersonGroup)
            .orElseThrow(AzureNotFoundException::new);

        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("%s/personGroups/%s/training",
            applicationProperties.getFaceApi().getEndpoint(),
            personGroup.getPersonGroupId()
        );

        ResponseEntity<TrainingStatusDTO> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<Void>(null, getHttpHeaders()),
            TrainingStatusDTO.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new AzureFaceApiException(response.getStatusCode().getReasonPhrase());
        }

        return response.getBody();
    }

    public Face addFaceWithUrl(String imageUrl, PersonGroupPerson person) {
        logger.info("Submitting new person to FaceAPI: {}", person);

        if (person.getPersonGroup() == null) {
            throw new AzureFaceApiException("No person group");
        }
        if (person.getId() != null || person.getPersonId() != null) {
            throw new AzureFaceApiException("Person already created");
        }
        if (StringUtils.isEmpty(person.getName())) {
            throw new AzureFaceApiException("No name specified");
        }
        String personGroupId = person.getPersonGroup().getPersonGroupId();
        String url = String.format("%s/personGroups/%s/persons/%s/persistedFaces",
            applicationProperties.getFaceApi().getEndpoint(),
            personGroupId,
            person.getPersonId());

        logger.info("Sending request to {}", url);
        Map<String, String> body = new HashMap<>();
        body.put("url", imageUrl);

        HttpHeaders headers = getHttpHeaders();

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        RestTemplate template = new RestTemplate();
        ResponseEntity<FaceAddResponse> resp = template.exchange(url, HttpMethod.POST, request, FaceAddResponse.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new AzureFaceApiException("Could not add face: " + resp.toString());
        }

        FaceAddResponse resBody = resp.getBody();
        Face face = new Face();
        face.setPersistedFaceId(resBody.getPersistedFaceId());
        face.setPersonGroupPerson(person);
        return face;

    }

    @Transactional
    public PersonFaceSubmission addFaceWithFaceDetection(Long fdid, Long personId) throws IOException {

        FaceDetection faceDetection = faceDetectionRepository.findById(fdid).orElseThrow(AzureNotFoundException::new);
        PersonGroupPerson person = personRepository.findById(personId).orElseThrow(AzureNotFoundException::new);
        return addFaceWithFaceDetection(faceDetection, person);


    }

    public PersonFaceSubmission addFaceWithFaceDetection(FaceDetection fd, PersonGroupPerson person) throws IOException {
        logger.info("Submitting new face to person to FaceAPI: {}", fd.getFaceId(), person.getId());
        BufferedImage image;
        if (person.getPersonGroup() == null) {
            throw new AzureFaceApiException("No person group");
        }
        if (person.getId() == null || person.getPersonId() == null) {
            throw new AzureFaceApiException("Person not yet created");
        }
        File inputFile = new File(applicationProperties.getFaces().getPath(), String.format("%s.jpg", fd.getFaceId()));
        if (!inputFile.exists()) {
            throw new AzureFaceApiException("input file does not exist");
        }
        try {
            image = ImageIO.read(inputFile);
        } catch (IOException e) {
            throw new AzureFaceApiException(e);
        }
        String personGroupId = person.getPersonGroup().getPersonGroupId();
        String url = String.format("%s/personGroups/%s/persons/%s/persistedFaces",
            applicationProperties.getFaceApi().getEndpoint(),
            personGroupId,
            person.getPersonId());

        logger.info("Sending request to {}", url);
//        Map<String, String> body = new HashMap<>();
//        body.put("url", imageUrl);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        headers.add(API_KEY_HEADER, applicationProperties.getFaceApi().getApiKey());

        byte[] bytes = FileUtils.readFileToByteArray(inputFile);
        HttpEntity<byte[]> request = new HttpEntity<>(bytes, headers);
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = template.getMessageConverters();
        List<HttpMessageConverter<?>> newList = new ArrayList<>(messageConverters);
        newList.add(new BufferedImageHttpMessageConverter());
        template.setMessageConverters(newList);
        ResponseEntity<FaceAddResponse> resp = template.exchange(url, HttpMethod.POST, request, FaceAddResponse.class);

        if (resp.getStatusCode() != HttpStatus.OK) {
            throw new AzureFaceApiException("Could not add face: " + resp.toString());
        }

        FaceAddResponse resBody = resp.getBody();
        PersonFaceSubmission face = new PersonFaceSubmission();
        face.setPersistedFaceId(resBody.getPersistedFaceId());
        face.setFaceDetectionId(fd.getId());
        face.setPersonId(person.getId());
        return face;

    }

    List<PersonGroup> listGroups() {
        String url = String.format("%s/personGroups", applicationProperties.getFaceApi().getEndpoint());
        logger.info("Sending request to {}", url);

        HttpHeaders headers = getHttpHeaders();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GroupInfo[]> entity = restTemplate.getForEntity(url, GroupInfo[].class);

        if (entity.getStatusCode() != HttpStatus.OK) {
            throw new AzureFaceApiException("Failed to fetch groups: " + entity.toString());
        }

        GroupInfo[] groupsArray = entity.getBody();
        if (groupsArray.length > 0) {
            GroupInfo first = groupsArray[0];
            GroupInfo[] rest = ArrayUtils.remove(groupsArray, 0);
            List<GroupInfo> groups = Lists.asList(first, rest);
            return groups.stream()
                .map(g -> {
                    PersonGroup personGroup = new PersonGroup();
                    personGroup.setPersonGroupId(g.getPersonGroupId());
                    personGroup.setDescription(g.getUserData());
                    personGroup.setName(g.getName());
                    return personGroup;
                }).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }

    }

    private static class Error {
        private String code;

        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "Error{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
        }

    }

    private static class CreatePersonResponse {
        private String personId;

        private Error error;

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }

        @Override
        public String toString() {
            return "CreatePersonResponse{" +
                "personId='" + personId + '\'' +
                ", error=" + error +
                '}';
        }

    }

    private static class GroupInfo {
        private String personGroupId;
        private String name;

        private String userData;

        public String getPersonGroupId() {
            return personGroupId;
        }

        public void setPersonGroupId(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserData() {
            return userData;
        }

        public void setUserData(String userData) {
            this.userData = userData;
        }

    }

    private static class FaceAddResponse {

        private String persistedFaceId;

        public String getPersistedFaceId() {
            return persistedFaceId;
        }

        public void setPersistedFaceId(String persistedFaceId) {
            this.persistedFaceId = persistedFaceId;
        }

    }

}
