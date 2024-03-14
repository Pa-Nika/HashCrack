package ru.nsu.panova.main.worker.service.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "interaction.interface", havingValue = "http", matchIfMissing = true)
public class RestTemplateWrapper {

    private final RestTemplate restTemplate;


    @Retryable(value = RestClientException.class, backoff = @Backoff(delay = 2000L))
    public <T> ResponseEntity<T> post(String url, Object request, Class<T> responseType) {
        return restTemplate.postForEntity(url, request, responseType);
    }
}
