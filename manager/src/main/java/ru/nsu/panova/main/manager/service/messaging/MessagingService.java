package ru.nsu.panova.main.manager.service.messaging;

import ru.nsu.panova.main.models.worker.request.PartialHashCrackingRequest;

public interface MessagingService {

    void sendTaskToWorker(PartialHashCrackingRequest request);

}
