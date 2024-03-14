package ru.nsu.panova.main.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.manager.service.messaging.MessagingService;
import ru.nsu.panova.main.manager.service.separator.TaskSeparator;
import ru.nsu.panova.main.manager.service.storage.CrackTaskBatchStorage;
import ru.nsu.panova.main.manager.service.storage.CrackTasksBatch;
import ru.nsu.panova.main.models.manager.entity.CrackStatus;
import ru.nsu.panova.main.models.manager.entity.CrackTask;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;
import ru.nsu.panova.main.models.manager.request.HashCrackRequest;
import ru.nsu.panova.main.models.manager.response.HashCrackResponse;
import ru.nsu.panova.main.models.manager.response.HashCrackStatusResponse;
import ru.nsu.panova.main.models.worker.request.PartialHashCrackingRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrackingService {

    private final TaskSeparator taskSeparator;
    private final CrackTaskBatchStorage crackTaskBatchStorage;
    private final MessagingService messagingService;

    public HashCrackResponse startHashCracking(HashCrackRequest request) {
        CrackTasksBatch crackTasksBatch = taskSeparator.separateCrackTask(request.getHash(), request.getMaxLength());

        String requestId = UUID.randomUUID().toString();
        crackTaskBatchStorage.addCrackTasksBatch(requestId, crackTasksBatch);

        for (CrackTask crackTask : crackTasksBatch.getCrackTaskList()) {
            messagingService.sendTaskToWorker(new PartialHashCrackingRequest(requestId, crackTask.getTaskId(), crackTask));
        }

        return new HashCrackResponse(requestId);
    }

    public HashCrackStatusResponse getCrackStatusByRequestId(String requestId) {
        CrackTasksBatch crackTasksBatch = crackTaskBatchStorage.getCrackTasksBatchByRequestId(requestId);
        boolean isStealInProgress = false;

        List<String> results = new ArrayList<>();

        for (CrackTask crackTask : crackTasksBatch.getCrackTaskList()) {
            if (crackTask.getStatus().equals(TaskStatus.ERROR)) {
                return new HashCrackStatusResponse(CrackStatus.ERROR, crackTask.getErrorMessage());
            }

            if (crackTask.getStatus() == TaskStatus.IN_PROGRESS) {
                isStealInProgress = true;
            }

            if (crackTask.getStatus() == TaskStatus.SUCCESS) {
                results.addAll(crackTask.getData());
            }
        }

        if (isStealInProgress) {
            return new HashCrackStatusResponse(CrackStatus.IN_PROGRESS);
        }

        return new HashCrackStatusResponse(CrackStatus.READY, results);
    }
}
