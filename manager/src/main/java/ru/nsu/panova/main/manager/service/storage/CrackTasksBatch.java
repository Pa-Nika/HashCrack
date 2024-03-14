package ru.nsu.panova.main.manager.service.storage;

import ru.nsu.panova.main.models.manager.entity.CrackTask;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrackTasksBatch {

    private final Map<String, CrackTask> tasksBatch = new HashMap<>();

    public void addCrackTask(CrackTask crackTask) {
        tasksBatch.put(crackTask.getTaskId(), crackTask);
    }

    public void updateCrackTaskStatus(TaskStatus newStatus, String taskId) {
        CrackTask crackTask = tasksBatch.get(taskId);
        crackTask.setStatus(newStatus);
        tasksBatch.put(taskId, crackTask);
    }

    public void updateErrorCrackTask(String taskId, String errorMessage) {
        CrackTask crackTask = tasksBatch.get(taskId);
        crackTask.setStatus(TaskStatus.ERROR);
        crackTask.setErrorMessage(errorMessage);
        tasksBatch.put(taskId, crackTask);
    }

    public void updateSuccessCrackTask(String taskId, List<String> data) {
        CrackTask crackTask = tasksBatch.get(taskId);
        crackTask.setStatus(TaskStatus.SUCCESS);
        crackTask.setData(new ArrayList<>(data));
        tasksBatch.put(taskId, crackTask);
    }

    public List<CrackTask> getCrackTaskList() {
        return tasksBatch.values().stream().toList();
    }
}
