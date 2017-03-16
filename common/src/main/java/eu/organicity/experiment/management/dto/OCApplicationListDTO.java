package eu.organicity.experiment.management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OCApplicationListDTO {

    private List<OCApplicationDTO> applications;

    public List<OCApplicationDTO> getApplications() {
        return applications;
    }

    public void setApplications(List<OCApplicationDTO> applications) {
        this.applications = applications;
    }
}
