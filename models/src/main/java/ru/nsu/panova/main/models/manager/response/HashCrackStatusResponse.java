package ru.nsu.panova.main.models.manager.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsu.panova.main.models.manager.entity.CrackStatus;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HashCrackStatusResponse {
    private CrackStatus status;
    private List<String> data;
    private String errorMessage;

    public HashCrackStatusResponse(CrackStatus status) {
        this(status, Collections.emptyList(), null);
    }

    public HashCrackStatusResponse(CrackStatus status, String errorMessage) {
        this(status, Collections.emptyList(), errorMessage);
    }

    public HashCrackStatusResponse(CrackStatus status, List<String> data) {
        this(status, data, null);
    }
}
