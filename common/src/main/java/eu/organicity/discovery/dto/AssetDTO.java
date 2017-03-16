package eu.organicity.discovery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AssetDTO {
    private String id;
    private String type;
    private ContextDTO context;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ContextDTO getContext() {
        return context;
    }

    public void setContext(ContextDTO context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AssetDTO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
