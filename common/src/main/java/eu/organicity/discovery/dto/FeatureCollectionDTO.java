package eu.organicity.discovery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureCollectionDTO {
    private String type;
    private PropertyDTO properties;
    private List<FeatureDTO> features;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PropertyDTO getProperties() {
        return properties;
    }

    public void setProperties(PropertyDTO properties) {
        this.properties = properties;
    }

    public List<FeatureDTO> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDTO> features) {
        this.features = features;
    }
}
