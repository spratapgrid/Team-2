package com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.model.EducationDTO;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.model.ParsedResumeDTO;
import com.forge.talentAcquisitionEngine.candidateService.resumeParser.aiengine.model.WorkExperienceDTO;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ResumeMappingService {

    public ParsedResumeDTO mapToDTO(JsonNode affindaData) {
        JsonNode dataNode = affindaData.path("data");


        String firstName = dataNode.path("name").path("first").asText(null);
        String lastName = dataNode.path("name").path("last").asText(null);

        String email = dataNode.path("emails").isArray() && dataNode.path("emails").size() > 0
                ? dataNode.path("emails").get(0).asText(null) : null;

        String phone = dataNode.path("phoneNumbers").isArray() && dataNode.path("phoneNumbers").size() > 0
                ? dataNode.path("phoneNumbers").get(0).asText(null) : null;

        String location = dataNode.path("location").path("formatted").asText(null);
        String summary = dataNode.path("summary").asText(null);


        List<String> skills = extractStringList(dataNode.path("skills"), "name");
        List<String> languages = extractStringList(dataNode.path("languages"), "language");
        List<String> websites = extractStringList(dataNode.path("websites"), "url"); // Or whatever field Affinda uses


        List<WorkExperienceDTO> workList = new ArrayList<>();
        if (dataNode.path("workExperience").isArray()) {
            for (JsonNode job : dataNode.path("workExperience")) {
                workList.add(new WorkExperienceDTO(
                        job.path("jobTitle").asText(null),
                        job.path("organization").asText(null),
                        job.path("dates").path("startDate").asText(null),
                        job.path("dates").path("endDate").asText(null),
                        job.path("location").path("formatted").asText(null),
                        job.path("jobDescription").asText(null)
                ));
            }
        }


        List<EducationDTO> eduList = new ArrayList<>();
        if (dataNode.path("education").isArray()) {
            for (JsonNode edu : dataNode.path("education")) {
                eduList.add(new EducationDTO(
                        edu.path("organization").asText(null),
                        edu.path("accreditation").path("educationLevel").asText(null),
                        edu.path("accreditation").path("education").asText(null),
                        edu.path("dates").path("startDate").asText(null),
                        edu.path("dates").path("completionDate").asText(null),
                        edu.path("grade").path("value").asText(null)
                ));
            }
        }


        return new ParsedResumeDTO(
                firstName, lastName, email, phone, location, summary,
                websites, skills, languages, workList, eduList
        );
    }


    private List<String> extractStringList(JsonNode node, String fieldName) {
        List<String> list = new ArrayList<>();
        if (node.isArray()) {
            for (JsonNode item : node) {
                list.add(item.path(fieldName).asText());
            }
        }
        return list;
    }
}