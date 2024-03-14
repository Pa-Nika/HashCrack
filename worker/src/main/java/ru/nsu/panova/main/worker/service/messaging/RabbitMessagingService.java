package ru.nsu.panova.main.worker.service.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "interaction.interface", havingValue = "rabbit")
public class RabbitMessagingService implements MessagingService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendTaskToManager(CrackingTaskStatusUpdateRequest request) {

    }
}
