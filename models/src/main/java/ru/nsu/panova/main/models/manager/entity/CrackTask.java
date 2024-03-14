package ru.nsu.panova.main.models.manager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CrackTask {
    private String taskId;
    private String hash;
    private Integer partNumber;
    private Integer partCount;
    private TaskStatus status;
    private String errorMessage;
    private List<String> data;
}
