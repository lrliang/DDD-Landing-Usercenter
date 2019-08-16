package com.llr.vendor;


import com.llr.exceptions.BusinessException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SendCloudEmailClient {

    private static final String EMAIL_URL = "http://api.sendcloud.net/apiv2/mail/sendtemplate";
    private static final String API_USER = "HackerStart_test_z6Zzqm";
    private static final String API_KEY = "3yPMlYyWFkOcGGaH";
    private static final String EMAIL_SENDER = "tech@gmail.com";

    private final RestTemplate restTemplate;

    public SendCloudEmailClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public boolean sendTemplateEmail(String xsmtpapi,
                                                    String templateInvokeName) throws BusinessException {

        LinkedMultiValueMap<String, String> allRequestParams = new LinkedMultiValueMap<>();
        allRequestParams.add("apiUser", API_USER);
        allRequestParams.add("apiKey", API_KEY);
        allRequestParams.add("from", EMAIL_SENDER);
        allRequestParams.add("xsmtpapi", xsmtpapi);
        allRequestParams.add("templateInvokeName", templateInvokeName);

        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(EMAIL_URL)
                .queryParams(allRequestParams);
        UriComponents uriComponents = builder.build().encode();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toUri(), HttpMethod.POST, requestEntity, String.class);

        boolean sendEmailSuccess = response.getStatusCode().is2xxSuccessful();

        if (!sendEmailSuccess) {
            throw new BusinessException("Send email failed!" + xsmtpapi);
        }
        return sendEmailSuccess;
    }
}
