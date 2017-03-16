package eu.organicity.client.client;


import eu.organicity.client.android.OrganicityAndroidClient;

public class OrganicityClient extends OrganicityAndroidClient {

    public OrganicityClient() {
        super();
    }

    public OrganicityClient(final String token) {
        super(token);
    }

    public OrganicityClient(String client_id, String client_secret, String username, String password) {
        super(client_id, client_secret, username, password);
    }
}
