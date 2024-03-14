package ru.nsu.panova.main.worker.service.combination;

import java.util.ArrayList;
import java.util.List;

public class BaseCombinator<T> {

    private final List<Integer> stateArray;

    private final List<T> alphabet;
    private final Integer partCount;

    private Integer currentCombinationNumber = 0;

    /**
     * @param alphabet
     * @param partNumber начинается с 0!
     * @param partCount
     */

    public BaseCombinator(List<T> alphabet, Integer partNumber, Integer partCount) {
        this.alphabet = alphabet;
        this.partCount = partCount;
        this.stateArray = initFirstCombination(partNumber, partCount, alphabet);

    }

    private List<Integer> initFirstCombination(Integer partNumber, Integer partCount, List<?> alphabet) {
        int startPosition = partNumber * partCount - 1;
        List<Integer> startStateArray = new ArrayList<>();

        if (startPosition == -1) {
            startStateArray.add(-1);
            return startStateArray;
        }

        while (startPosition > 0) {
            startStateArray.add(startPosition % alphabet.size());
            startPosition = startPosition / alphabet.size();
        }
        return startStateArray;
    }

    /**
     * Лютый кринж, не смотрите, потом поправлю.
     *
     * @return
     */
    public List<T> getNextCombination() {
        for (int i = 0; ; i++) {

            if (++currentCombinationNumber > partCount) {
                // Эксепшен что ну хорэ уже (потом будет)
            }

            int currentPositionState = stateArray.get(i);
            if (currentPositionState + 1 == alphabet.size() - 1) {
                stateArray.set(i, 0);
                if (stateArray.size() == i + 1) {
                    stateArray.add(0);
                    break;
                }

                continue;
            }

            stateArray.set(i, currentPositionState + 1);
            break;
        }

        return stateArray.stream()
                .map(alphabet::get)
                .toList();
    }

}
