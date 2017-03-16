package eu.organicity.client.test;

import eu.organicity.client.client.OrganicityClient;
import eu.organicity.discovery.dto.AssetDTO;
import eu.organicity.discovery.dto.AssetTypeDTO;
import eu.organicity.discovery.dto.FeatureCollectionDTO;
import eu.organicity.discovery.dto.FeatureDTO;
import org.junit.Before;
import org.junit.Test;

public class OrganicityADSClientTests {

    private OrganicityClient client;

    @Before
    public void before() throws Exception {
        client = new OrganicityClient();
    }

    @Test
    public void testAssetTypes() throws Exception {
        final AssetTypeDTO[] assets = client.listAssetTypes();
        for (final AssetTypeDTO asset : assets) {
            System.out.println(asset);
        }
    }

    @Test
    public void testListNearbyAssets() throws Exception {
        final FeatureCollectionDTO[] assets = client.listNearbyAssets(38.246639, 21.734573, 20);
        for (final FeatureCollectionDTO asset : assets) {
            System.out.println(asset.getProperties().getName());
            for (final FeatureDTO featureDTO : asset.getFeatures()) {
                System.out.println("\t" + featureDTO.getProperties().getId() + " " + featureDTO.getGeometry());
            }
        }
    }

    @Test
    public void testListExperimentAssets() throws Exception {
        AssetDTO[] assetDTOs = client.listExperimentAssets("5820b17baeb046575877af53");
        for (final AssetDTO assetDTO : assetDTOs) {
            System.out.println(assetDTO);
        }

        assetDTOs = client.listExperimentAssets("57f210e59324fdd11103d93c");
        for (final AssetDTO assetDTO : assetDTOs) {
            System.out.println(assetDTO);
        }
    }
}
