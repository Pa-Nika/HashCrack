package ru.nsu.panova.main.models.manager.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HashCrackRequest {
    private String hash;
    private Integer maxLength;
}
