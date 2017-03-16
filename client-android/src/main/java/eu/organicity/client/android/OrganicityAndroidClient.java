package eu.organicity.client.android;

import eu.organicity.client.OrganicityServiceBaseClient;
import eu.organicity.discovery.dto.AssetDTO;
import eu.organicity.discovery.dto.AssetTypeDTO;
import eu.organicity.discovery.dto.FeatureCollectionDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;

public class OrganicityAndroidClient extends OrganicityServiceBaseClient {

    private static final String EXPERIMENTERS_SITE_ENDPOINT = "https://exp.orion.organicity.eu/v2/";
    private static final String ADS_ENDPOINT = "http://discovery.organicity.eu/v0/";

    private String applicationId;
    private String experimentId;

    public OrganicityAndroidClient() {
        this("");
    }

    public OrganicityAndroidClient(String client_id, String client_secret, String username, String password) {
        super(client_id, client_secret, username, password);
        restTemplate.setErrorHandler(new OrganicityErrorHandler());
    }

    public OrganicityAndroidClient(final String token) {
        super(token);
        restTemplate.setErrorHandler(new OrganicityErrorHandler());
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setExperimentId(String experimentId) {
        this.experimentId = experimentId;
    }

    public void setErrorHandler(final ResponseErrorHandler responseErrorHandler) {
        restTemplate.setErrorHandler(responseErrorHandler);
    }

    //    Experimenters Site API

    public String postAsset(final String entity) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add("X-Organicity-Application", applicationId);
        headers.add("X-Organicity-Experiment", experimentId);

        final HttpEntity<String> requestEntity = new HttpEntity<>(entity, headers);
        return restTemplate.exchange(EXPERIMENTERS_SITE_ENDPOINT + "entities", HttpMethod.POST, requestEntity, String.class).getBody();
    }

    public String updateAsset(final String assetId, final String entity) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add("X-Organicity-Application", applicationId);
        headers.add("X-Organicity-Experiment", experimentId);

        final HttpEntity<String> requestEntity = new HttpEntity<>(entity, headers);
        return restTemplate.exchange(EXPERIMENTERS_SITE_ENDPOINT + "entities/" + assetId + "/attrs", HttpMethod.POST, requestEntity, String.class).getBody();
    }


    public void deleteAsset(String assetId) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + getToken());
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add("X-Organicity-Application", applicationId);
        headers.add("X-Organicity-Experiment", experimentId);

        final HttpEntity requestEntity = new HttpEntity(headers);
        restTemplate.exchange(EXPERIMENTERS_SITE_ENDPOINT + "entities/" + assetId, HttpMethod.DELETE, requestEntity, String.class).getBody();
    }

    //    Discovery API

    /**
     * List all nearby assets.
     *
     * @param lat    the latitude for the query.
     * @param lon    the longitude for the query.
     * @param radius the radius for the circle to search for assets inside it.
     * @return a {@see FeatureCollectionDTO} array.
     */
    public FeatureCollectionDTO[] listNearbyAssets(final double lat, final double lon, final int radius) {

        final MultiValueMap<String, String> codeMap = new LinkedMultiValueMap<>();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.USER_AGENT, "java-client");
        final HttpEntity<MultiValueMap<String, String>> internalRec = new HttpEntity<>(codeMap, headers);
        return restTemplate.exchange(ADS_ENDPOINT + "assets/geo/search?lat=" + lat + "&long=" + lon + "&radius=" + radius + "&km=true", HttpMethod.GET, internalRec, FeatureCollectionDTO[].class).getBody();
    }

    /**
     * List all asset types.
     *
     * @return a {@see AssetTypeDTO} array.
     */
    public AssetTypeDTO[] listAssetTypes() {
        final MultiValueMap<String, String> codeMap = new LinkedMultiValueMap<>();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.USER_AGENT, "java-client");
        final HttpEntity<MultiValueMap<String, String>> internalRec = new HttpEntity<>(codeMap, headers);
        return restTemplate.exchange(ADS_ENDPOINT + "types", HttpMethod.GET, internalRec, AssetTypeDTO[].class).getBody();
    }

    /**
     * List all experiment assets.
     *
     * @param experimentId the id of the experiment for the query.
     * @return a {@see AssetDTO} array.
     */
    public AssetDTO[] listExperimentAssets(final String experimentId) {
        final MultiValueMap<String, String> codeMap = new LinkedMultiValueMap<>();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.USER_AGENT, "java-client");
        final HttpEntity<MultiValueMap<String, String>> internalRec = new HttpEntity<>(codeMap, headers);
        try {
            return restTemplate.exchange(ADS_ENDPOINT + "assets/experiments/" + experimentId, HttpMethod.GET, internalRec, AssetDTO[].class).getBody();
        } catch (Exception e) {
            return new AssetDTO[]{restTemplate.exchange(ADS_ENDPOINT + "assets/experiments/" + experimentId, HttpMethod.GET, internalRec, AssetDTO.class).getBody()};
        }
    }

    public AssetDTO adsGetAsset(String assetId) {

        final MultiValueMap<String, String> codeMap = new LinkedMultiValueMap<>();
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.USER_AGENT, "java-client");
        final HttpEntity<MultiValueMap<String, String>> internalRec = new HttpEntity<>(codeMap, headers);
        try {
            return restTemplate.exchange(ADS_ENDPOINT + "assets/" + assetId, HttpMethod.GET, internalRec, AssetDTO.class).getBody();
        } catch (Exception e) {
            String resp = restTemplate.exchange(ADS_ENDPOINT + "assets/" + assetId, HttpMethod.GET, internalRec, String.class).getBody();
            if ("[]".equals(resp)) {
                return null;
            } else {
                return null;
            }
        }
    }

}
