package eu.organicity.client.test;

import eu.organicity.client.client.OrganicityClient;
import eu.organicity.discovery.dto.AssetDTO;
import junit.framework.Assert;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static eu.organicity.client.test.Constants.APPLICATION_ID;
import static eu.organicity.client.test.Constants.CLIENT_ID;
import static eu.organicity.client.test.Constants.CLIENT_SECRET;
import static eu.organicity.client.test.Constants.EXPERIMENTER_ID;
import static eu.organicity.client.test.Constants.EXPERIMENT_ID;
import static eu.organicity.client.test.Constants.EXPERIMENT_SCOPE;
import static eu.organicity.client.test.Constants.PASSWORD;
import static eu.organicity.client.test.Constants.USERNAME;

public class OrganicityExpProxyClientTests {

    private OrganicityClient client;

    private static String ASSET_ID = "urn:oc:entity:experimenters:" + EXPERIMENTER_ID + ":" + EXPERIMENT_ID + ":1";

    @Before
    public void before() throws Exception {
        PropertyConfigurator.configure("log4j.properties");
        client = new OrganicityClient(CLIENT_ID, CLIENT_SECRET, USERNAME, PASSWORD);
        client.setExperimentId(EXPERIMENT_ID);
        client.setApplicationId(APPLICATION_ID);
    }

    @Test
    public void testExpProxyOperationsPublic() throws Exception {
        runTestExpProxyOperations(EXPERIMENT_SCOPE);
    }

    public void runTestExpProxyOperations(final String type) throws Exception {
        AssetDTO asset = readAsset(ASSET_ID);
        if (asset != null) {
            deleteAsset();
        }

        createAsset();
        Thread.sleep(60000);
        asset = readAsset(ASSET_ID);

        Assert.assertNotNull(asset);
        Assert.assertNotNull(asset.getContext());
        Assert.assertEquals(asset.getContext().getScope(), type);

        updateAsset();

        deleteAsset();
        Thread.sleep(60000);
        asset = readAsset(ASSET_ID);
        Assert.assertNull(asset);


    }

    public void createAsset() throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", ASSET_ID);
        json.put("type", "urn:oc:entityType:iotdevice");
        json.put("TimeInstant", new JSONObject());
        json.getJSONObject("TimeInstant").put("type", "urn:oc:attributeType:ISO8601");
        json.getJSONObject("TimeInstant").put("value", "2017-03-16T11:52:38.881Z");
        client.postAsset(json.toString());
    }

    private AssetDTO readAsset(String assetId) {
        AssetDTO asset = client.adsGetAsset(assetId);
        return asset;
    }

    public void updateAsset() throws Exception {
        JSONObject json = new JSONObject();
        json.put("TimeInstant", new JSONObject());
        json.getJSONObject("TimeInstant").put("type", "urn:oc:attributeType:ISO8601");
        json.getJSONObject("TimeInstant").put("value", "2017-03-16T11:52:38.881Z");
        client.updateAsset(ASSET_ID, json.toString());
    }

    public void deleteAsset() throws Exception {
        client.deleteAsset(ASSET_ID);
    }
}
