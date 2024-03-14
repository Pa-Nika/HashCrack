package ru.nsu.panova.main.worker.service;

import jakarta.xml.bind.DatatypeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.panova.main.models.manager.entity.CrackTask;
import ru.nsu.panova.main.models.manager.entity.TaskStatus;
import ru.nsu.panova.main.models.worker.request.CrackingTaskStatusUpdateRequest;
import ru.nsu.panova.main.models.worker.request.PartialHashCrackingRequest;
import ru.nsu.panova.main.models.worker.response.PartialHashCrackingResponse;
import ru.nsu.panova.main.worker.service.combination.BaseCombinator;
import ru.nsu.panova.main.worker.service.messaging.MessagingService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrackingService {

    private static final List<Character> ALPHABET = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');

    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private final MessagingService messagingService;

    public PartialHashCrackingResponse startCrackingRoutine(PartialHashCrackingRequest request) {
        CompletableFuture.runAsync(() -> crack(request), executorService);
        return new PartialHashCrackingResponse(request.getRequestId(), request.getTaskId());
    }

    private void crack(PartialHashCrackingRequest request) {
        CrackTask crackTask = request.getCrackTask();
        Integer partCount = crackTask.getPartCount();


        BaseCombinator<Character> baseCombinator = new BaseCombinator(ALPHABET, crackTask.getPartNumber(), partCount);

        List<String> result = new ArrayList<>();

        for (int i = 0; i < partCount; i++) {
            String combination = getStringRepresentation(baseCombinator.getNextCombination());

            log.info("combination[{}]={}", i, combination);
            if (getMD5Hash(combination).equals(request.getCrackTask().getHash())) {
                result.add(combination);
            }
        }


        if (!result.isEmpty()) {
            crackTask.setStatus(TaskStatus.SUCCESS);
            crackTask.setData(result);
        } else {
            crackTask.setStatus(TaskStatus.FAILED);
        }

        messagingService.sendTaskToManager(new CrackingTaskStatusUpdateRequest(request.getRequestId(), request.getTaskId(), crackTask.getStatus(), crackTask.getErrorMessage(), crackTask.getData()));

    }

    private String getStringRepresentation(List<Character> list) {
        StringBuilder builder = new StringBuilder(list.size());
        for (Character ch : list) {
            builder.append(ch);
        }

        return builder.toString();
    }

    private String getMD5Hash(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(str.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest);
    }
}
