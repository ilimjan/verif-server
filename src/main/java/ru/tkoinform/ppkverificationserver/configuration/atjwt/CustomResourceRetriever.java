package ru.tkoinform.ppkverificationserver.configuration.atjwt;

import com.nimbusds.jose.util.Resource;
import com.nimbusds.jose.util.ResourceRetriever;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class CustomResourceRetriever implements ResourceRetriever {
    private static final MediaType APPLICATION_JWK_SET_JSON = new MediaType("application", "jwk-set+json");
    private final RestOperations restOperations;

    public CustomResourceRetriever(RestOperations restOperations) {
        Assert.notNull(restOperations, "restOperations cannot be null");
        this.restOperations = restOperations;
    }

    public Resource retrieveResource(URL url) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, APPLICATION_JWK_SET_JSON));

        ResponseEntity response;
        try {
            RequestEntity<Void> request = new RequestEntity(headers, HttpMethod.GET, url.toURI());
            response = this.restOperations.exchange(request, String.class);
        } catch (Exception var5) {
            throw new IOException(var5);
        }

        if (response.getStatusCodeValue() != 200) {
            throw new IOException(response.toString());
        } else {
            return new Resource((String)response.getBody(), "UTF-8");
        }
    }
}
