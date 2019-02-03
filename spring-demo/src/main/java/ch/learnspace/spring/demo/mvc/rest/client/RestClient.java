package ch.learnspace.spring.demo.mvc.rest.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {

    public <T> ResponseEntity<T> postForEntity(String url, Object request, Class<T> responseType,
                                               Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.postForEntity(url, request, responseType, uriVariables);
        } catch (org.springframework.web.client.RestClientException e) {
            throw prepareException(e);
        }
    }

    public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType,
                                              Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForEntity(url, responseType, uriVariables);
        } catch (org.springframework.web.client.RestClientException e) {
            throw prepareException(e);
        }
    }

    private RestClientException prepareException(Exception e) {
        String errorMessage = e.getMessage();
        if (e instanceof HttpStatusCodeException) {
            String responseBody = ((HttpStatusCodeException) e).getResponseBodyAsString();
            if (responseBody != null && !responseBody.isEmpty()) {
                errorMessage = responseBody;
            }
        }
        return new RestClientException(errorMessage);
    }
}
