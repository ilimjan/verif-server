package ru.tkoinform.ppkverificationserver.configuration.ppk;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.PlainJWT;
import org.apache.http.Header;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PpkJwtDecoder implements JwtDecoder {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String identificationServerUri;

    // Мапа для кэширования валидированных токенов (чтобы не ддосить сервер авторизации)
    private static final Map<String, Date> validatedTokens = new HashMap<String, Date>();
    private static final Long cacheValidatedTokenTime = 30 * 1000L;
    private static final Integer cacheSize = 100;

    @Override
    public Jwt decode(String token) throws JwtException {
        JWT jwt = parse(token);
        if (jwt instanceof PlainJWT) {
            throw new BadJwtException("Unsupported algorithm of " + jwt.getHeader().getAlgorithm());
        }
        Jwt createdJwt = createJwt(token, jwt);
        return validateJwt(createdJwt);
    }

    private JWT parse(String token) {
        try {
            return JWTParser.parse(token);
        } catch (Exception ex) {
            throw new BadJwtException(ex.getMessage(), ex);
        }
    }

    private Jwt createJwt(String token, JWT parsedJwt) {
        try {
            Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
            Map<String, Object> claims = new LinkedHashMap<>(parsedJwt.getJWTClaimsSet().toJSONObject());

            for (String key : claims.keySet()) {
                // Эти claims обязательно должны быть Instant, нужно их предварительно конвертнуть
                if (key.equals(JwtClaimNames.IAT) || key.equals(JwtClaimNames.EXP)) {
                    claims.put(key, Instant.ofEpochMilli((Long) claims.get(key)));
                }
            }

            return Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(headers))
                    .claims(c -> c.putAll(claims))
                    .build();
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);

            if (ex.getCause() instanceof ParseException) {
                throw new BadJwtException("Malformed payload");
            } else {
                throw new BadJwtException(ex.getMessage(), ex);
            }
        }
    }

    /**
     * От разработчиков сервера авторизации:
     * "Механизм интроспекции внутри проекта не настроен и требует значительного времени. предлагаю следующий workaround:
     *  дергать https://id.test.reo.ru/connect/userinfo
     *  в параметр отдаем токен, если сервер возвращает ок  (200) то токен валиден, если 401 то токен не валиден"
     *
     * @param jwt
     * @return Jwt
     * @throws JwtException
     */
    private Jwt validateJwt(Jwt jwt) {
        Date now = new Date();
        Date cachedDate = validatedTokens.get(jwt.getTokenValue());
        if (cachedDate == null || now.getTime() - cachedDate.getTime() > cacheValidatedTokenTime) {
            // Либо токена нет в кэше, либо время кэширования истекло

            Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwt.getTokenValue()));
            List<Header> headers = Collections.singletonList(authHeader);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .setDefaultHeaders(headers)
                    .build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            try {
                ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(this.identificationServerUri + "/connect/userinfo", HttpMethod.GET, null, String.class);
                if (response.getStatusCode().value() != 200) {
                    logger.warn("Token is not validated by identity server!");
                    throw new JwtException("Token is not validated by identity server!");
                } else {
                    logger.debug("Token is validated by identity server");
                }
            } catch (HttpClientErrorException e) {
                logger.warn(String.format("Token is not validated by identity server: %s", e.getMessage()));
                throw new JwtException(String.format("Token is not validated by identity server: %s", e.getMessage()), e);
            }

            validatedTokens.put(jwt.getTokenValue(), now);
        } else {
            logger.debug("Token is validated (found in cache)");
        }

        synchronized (validatedTokens) {
            if (validatedTokens.size() > cacheSize) {
                logger.debug("Clear token cache");
                validatedTokens.clear();
            }
        }

        return jwt;
    }
}
