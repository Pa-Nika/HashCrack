package ru.nsu.panova.main.worker.service.messaging;

import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;

public interface MessagingService {

    void sendTaskToManager(CrackingTaskStatusUpdateRequest request);

}
