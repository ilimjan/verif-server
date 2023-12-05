package ru.tkoinform.ppkverificationserver.service;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.compress.utils.CharsetNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

@Service
public class PpkService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${ppk.api.url}")
    private String ppkApiUrl;

    public void sendEmail(String email, String topic, String body, File file, String token) throws IOException {
        if (StringUtils.isEmpty(email)) {
            throw new IllegalArgumentException("Email is not defined!");
        }
        logger.info("Sending email to: {}", email);

        email = URLEncoder.encode(email, CharsetNames.UTF_8);
        topic = URLEncoder.encode(topic, CharsetNames.UTF_8);
        body = URLEncoder.encode(body, CharsetNames.UTF_8);
        String url = ppkApiUrl + "/emails/api/Email/Send?emailTo=" + email + "&subject=" + topic + "&body=" + body;

        /*
        // TODO: не понятно почему не работает (возвращает 400, скорее всего неправильно передается файл)
        Header authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", token));
        List<Header> headers = Collections.singletonList(authHeader);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .setDefaultHeaders(headers)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        LinkedMultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        InputStream in = new FileInputStream(file);
        bodyMap.add("file", new FileSystemResource(file));


        HttpHeaders bodyHeaders = new HttpHeaders();
        bodyHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        bodyHeaders.setBearerAuth(token);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, bodyHeaders);

        ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        */
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        okhttp3.RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), okhttp3.RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("POST", requestBody)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = client.newCall(request).execute();

        logger.info("SendEmail response: {}", response.body());
    }

}
