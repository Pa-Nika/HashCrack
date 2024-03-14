package ru.nsu.panova.main.manager.service.separator;

import ru.nsu.panova.main.manager.service.storage.CrackTasksBatch;

public interface TaskSeparator {

    CrackTasksBatch separateCrackTask(String hash, Integer maxLength);

}
