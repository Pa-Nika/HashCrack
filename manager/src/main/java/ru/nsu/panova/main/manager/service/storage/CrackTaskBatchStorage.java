package ru.nsu.panova.main.manager.service.storage;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CrackTaskBatchStorage {

    private Map<String, CrackTasksBatch> tasksBatchStorage = new HashMap<>();

    public void addCrackTasksBatch(String requestId, CrackTasksBatch crackTasksBatch) {
        tasksBatchStorage.put(requestId, crackTasksBatch);
    }

    public void updateTaskStatus(String requestId, String taskId, TaskStatus taskStatus) {
        tasksBatchStorage.get(requestId).updateCrackTaskStatus(taskStatus, taskId);
    }

    public void updateErrorTask(String requestId, String taskId, String errorMessage) {
        tasksBatchStorage.get(requestId).updateErrorCrackTask(taskId, errorMessage);
    }

    public void updateSuccessTask(String requestId, String taskId, List<String> data) {
        tasksBatchStorage.get(requestId).updateSuccessCrackTask(taskId, data);
    }

    public CrackTasksBatch getCrackTasksBatchByRequestId(String requestId) {
        return tasksBatchStorage.get(requestId);
    }

}
