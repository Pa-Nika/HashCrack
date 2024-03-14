package ru.nsu.panova.main.manager.service.separator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.manager.service.storage.CrackTasksBatch;
import ru.nsu.panova.main.models.manager.entity.CrackTask;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;

import java.util.UUID;

@Service
public class SimpleTaskSeparator implements TaskSeparator {

    @Value("${worker.count}")
    private Integer workerCount;

    private static final Integer ALPHABET_LENGTH = 26;

    @Override
    public CrackTasksBatch separateCrackTask(String hash, Integer maxLength) {
        CrackTasksBatch crackTasksBatch = new CrackTasksBatch();

        int operationCount = (int) (Math.pow(ALPHABET_LENGTH + 1, maxLength) - 1);


        for (int i = 0; i < workerCount; i++) {
            CrackTask crackTask = new CrackTask();
            crackTask.setTaskId(UUID.randomUUID().toString());
            crackTask.setStatus(TaskStatus.NEW);
            crackTask.setHash(hash);
            crackTask.setPartNumber(i);
            crackTask.setPartCount(operationCount / workerCount);
            if (i == workerCount - 1) {
                crackTask.setPartCount(operationCount / workerCount + operationCount % workerCount);
            }

            crackTasksBatch.addCrackTask(crackTask);
        }

        return crackTasksBatch;
    }


}
