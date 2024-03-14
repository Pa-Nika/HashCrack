package ru.nsu.panova.main.manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.panova.main.manager.service.CrackingLifeCycleService;
import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;
import ru.nsu.panova.main.models.worker.response.CrackingTaskStatusUpdateResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/api/manager/hash")
public class HashCrackingInternalController {

    private final CrackingLifeCycleService crackingLifeCycleService;

    @PostMapping("/update-crack-status")
    public CrackingTaskStatusUpdateResponse updateHashCrackingState(@RequestBody CrackingTaskStatusUpdateRequest request) {
        return crackingLifeCycleService.updateCrackTaskState(request);
    }

}
