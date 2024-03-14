package ru.nsu.panova.main.models.worker.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartialHashCrackingResponse {
    private String requestId;
    private String taskId;
}
