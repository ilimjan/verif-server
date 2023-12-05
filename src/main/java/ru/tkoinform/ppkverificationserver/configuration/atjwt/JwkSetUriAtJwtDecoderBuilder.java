package ru.tkoinform.ppkverificationserver.configuration.atjwt;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.util.Resource;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.Assert;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Билдер для кастомного парсера JWT с типом AT+JWT
 * @see NimbusJwtDecoder.JwkSetUriJwtDecoderBuilder
 */
public final class JwkSetUriAtJwtDecoderBuilder {
    private String jwkSetUri;
    private Set<SignatureAlgorithm> signatureAlgorithms = new HashSet<>();
    private RestOperations restOperations = new RestTemplate();

    public JwkSetUriAtJwtDecoderBuilder(String jwkSetUri) {
        Assert.hasText(jwkSetUri, "jwkSetUri cannot be empty");
        this.jwkSetUri = jwkSetUri;
    }

    public JwkSetUriAtJwtDecoderBuilder jwsAlgorithm(SignatureAlgorithm signatureAlgorithm) {
        Assert.notNull(signatureAlgorithm, "signatureAlgorithm cannot be null");
        this.signatureAlgorithms.add(signatureAlgorithm);
        return this;
    }

    public JwkSetUriAtJwtDecoderBuilder jwsAlgorithms(Consumer<Set<SignatureAlgorithm>> signatureAlgorithmsConsumer) {
        Assert.notNull(signatureAlgorithmsConsumer, "signatureAlgorithmsConsumer cannot be null");
        signatureAlgorithmsConsumer.accept(this.signatureAlgorithms);
        return this;
    }

    public JwkSetUriAtJwtDecoderBuilder restOperations(RestOperations restOperations) {
        Assert.notNull(restOperations, "restOperations cannot be null");
        this.restOperations = restOperations;
        return this;
    }

    JWSKeySelector<SecurityContext> jwsKeySelector(JWKSource<SecurityContext> jwkSource) {
        if (this.signatureAlgorithms.isEmpty()) {
            return new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource);
        } else if (this.signatureAlgorithms.size() == 1) {
            JWSAlgorithm jwsAlgorithm = JWSAlgorithm.parse(this.signatureAlgorithms.iterator().next().getName());
            return new JWSVerificationKeySelector<>(jwsAlgorithm, jwkSource);
        } else {
            Map<JWSAlgorithm, JWSKeySelector<SecurityContext>> jwsKeySelectors = new HashMap<>();
            for (SignatureAlgorithm signatureAlgorithm : this.signatureAlgorithms) {
                JWSAlgorithm jwsAlg = JWSAlgorithm.parse(signatureAlgorithm.getName());
                jwsKeySelectors.put(jwsAlg, new JWSVerificationKeySelector<>(jwsAlg, jwkSource));
            }
            return new CustomJWSAlgorithmMapJWSKeySelector(jwsKeySelectors);
        }
    }

    JWTProcessor<SecurityContext> processor() {
        ResourceRetriever jwkSetRetriever = new CustomResourceRetriever(this.restOperations);
        JWKSource<SecurityContext> jwkSource = new RemoteJWKSet(toURL(this.jwkSetUri), jwkSetRetriever);
        ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor();

        JOSEObjectTypeVerifier ATJWT = new DefaultJOSEObjectTypeVerifier(new JOSEObjectType[]{new JOSEObjectType("AT+JWT"), null});
        jwtProcessor.setJWSTypeVerifier(ATJWT);
        jwtProcessor.setJWSKeySelector(this.jwsKeySelector(jwkSource));
        jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
        });
        return jwtProcessor;
    }

    public NimbusJwtDecoder build() {
        return new NimbusJwtDecoder(processor());
    }

    private static URL toURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Invalid JWK Set URL \"" + url + "\" : " + ex.getMessage(), ex);
        }
    }

    private static class RestOperationsResourceRetriever implements ResourceRetriever {
        private static final MediaType APPLICATION_JWK_SET_JSON = new MediaType("application", "jwk-set+json");
        private final RestOperations restOperations;

        RestOperationsResourceRetriever(RestOperations restOperations) {
            Assert.notNull(restOperations, "restOperations cannot be null");
            this.restOperations = restOperations;
        }

        @Override
        public Resource retrieveResource(URL url) throws IOException {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, APPLICATION_JWK_SET_JSON));

            ResponseEntity<String> response;
            try {
                RequestEntity<Void> request = new RequestEntity<>(headers, HttpMethod.GET, url.toURI());
                response = this.restOperations.exchange(request, String.class);
            } catch (Exception ex) {
                throw new IOException(ex);
            }

            if (response.getStatusCodeValue() != 200) {
                throw new IOException(response.toString());
            }

            return new Resource(response.getBody(), "UTF-8");
        }
    }
}
