package eu.organicity.discovery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureDTO {
    private String type;
    private GeometryDTO geometry;
    private FeaturePropertyDTO properties;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GeometryDTO getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryDTO geometry) {
        this.geometry = geometry;
    }

    public FeaturePropertyDTO getProperties() {
        return properties;
    }

    public void setProperties(FeaturePropertyDTO properties) {
        this.properties = properties;
    }
}
