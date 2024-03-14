package ru.nsu.panova.main.manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.manager.service.storage.CrackTaskBatchStorage;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;
import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;
import ru.nsu.panova.main.models.worker.response.CrackingTaskStatusUpdateResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrackingLifeCycleService {

    private final CrackTaskBatchStorage crackTaskBatchStorage;

    public CrackingTaskStatusUpdateResponse updateCrackTaskState(CrackingTaskStatusUpdateRequest crackingTaskStatusUpdateRequest) {
        switch (crackingTaskStatusUpdateRequest.getStatus()) {
            case NEW, FAILED, IN_PROGRESS ->
                updateCrackTaskState(
                        crackingTaskStatusUpdateRequest.getRequestId(),
                        crackingTaskStatusUpdateRequest.getTaskId(),
                        crackingTaskStatusUpdateRequest.getStatus()
                );
            case ERROR -> updateErrorCrackTask(
                    crackingTaskStatusUpdateRequest.getRequestId(),
                    crackingTaskStatusUpdateRequest.getTaskId(),
                    crackingTaskStatusUpdateRequest.getErrorMessage()
            );
            case SUCCESS -> updateSuccessCrackTask(
                    crackingTaskStatusUpdateRequest.getRequestId(),
                    crackingTaskStatusUpdateRequest.getTaskId(),
                    crackingTaskStatusUpdateRequest.getData()
            );
        }
        return new CrackingTaskStatusUpdateResponse(crackingTaskStatusUpdateRequest.getTaskId(), crackingTaskStatusUpdateRequest.getTaskId());
    }

    public void updateSuccessCrackTask(String requestId, String taskId, List<String> data) {
        crackTaskBatchStorage.updateSuccessTask(requestId, taskId, data);
    }

    public void updateErrorCrackTask(String requestId, String taskId, String errorMessage) {
        crackTaskBatchStorage.updateErrorTask(requestId, taskId, errorMessage);
    }

    public void updateCrackTaskState(String requestId, String taskId, TaskStatus taskStatus) {
        crackTaskBatchStorage.updateTaskStatus(requestId, taskId, taskStatus);
    }
}
