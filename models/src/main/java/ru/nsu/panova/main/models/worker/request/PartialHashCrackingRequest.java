package ru.nsu.panova.main.models.worker.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.nsu.panova.main.models.manager.entity.CrackTask;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PartialHashCrackingRequest {
    private String requestId;
    private String taskId;
    private CrackTask crackTask;
}
