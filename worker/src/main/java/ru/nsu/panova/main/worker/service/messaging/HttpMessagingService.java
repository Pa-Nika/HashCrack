package ru.nsu.panova.main.worker.service.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;
import ru.nsu.panova.main.models.worker.response.CrackingTaskStatusUpdateResponse;
import ru.nsu.panova.main.worker.exception.MessagingException;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "interaction.interface", havingValue = "http", matchIfMissing = true)
public class HttpMessagingService implements MessagingService {

    private final RestTemplateWrapper restTemplate;

    @Value("${http.manager.base.url}")
    private String baseUrl;

    @Override
    public void sendTaskToManager(CrackingTaskStatusUpdateRequest request) {
        ResponseEntity<CrackingTaskStatusUpdateResponse> responseEntity;
        try {
            responseEntity = restTemplate
                    .post(baseUrl + "internal/api/manager/hash/update-crack-status", request, CrackingTaskStatusUpdateResponse.class);

            if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                log.error("Response {} on {} requestId: {}, taskId: {}", responseEntity.getBody(), request, request.getRequestId(), request.getTaskId());
                throw new MessagingException("Error during http request send: " + responseEntity.getStatusCode()
                        + " requestId: " + request.getRequestId() + " taskId: " + request.getTaskId());
            }

        } catch (Exception e) {
            log.error("Error during http request send, requestId: " + request.getRequestId() + " taskId: " + request.getTaskId(), e);
        }
    }
}
