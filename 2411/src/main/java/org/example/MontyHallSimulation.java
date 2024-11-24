package org.example;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MontyHallSimulation {

    private static final int NUMBER_OF_TRIALS = 1000;
    private static final Random random = new Random();

    public static void main(String[] args) {
        Map<Integer, Result> results = new HashMap<>();

        for (int i = 1; i <= NUMBER_OF_TRIALS; i++) {
            boolean isWinning = playGame();
            results.put(i, new Result(isWinning));
        }

        DescriptiveStatistics stats = calculateStats(results);
        printResults(stats);
    }

    private static boolean playGame() {

        int numberOfDoors = 3;

        int winningDoor = random.nextInt(numberOfDoors) + 1;

        int chosenDoor = random.nextInt(numberOfDoors) + 1;

        int openedDoor = getOpenedDoor(winningDoor, chosenDoor);

        int switchedDoor = getSwitchedDoor(chosenDoor, openedDoor);

        return switchedDoor == winningDoor;
    }

    private static int getOpenedDoor(int winningDoor, int chosenDoor) {
        if (chosenDoor != winningDoor) {
            return getOtherDoor(winningDoor, chosenDoor);
        } else {
            return getRandomOtherDoor(winningDoor, chosenDoor);
        }
    }

    private static int getOtherDoor(int door1, int door2) {
        return 6 - door1 - door2;
    }

    private static int getRandomOtherDoor(int winningDoor, int chosenDoor) {
        int otherDoor = getOtherDoor(winningDoor, chosenDoor);
        int remainingDoor = random.nextBoolean() ? otherDoor : winningDoor;
        return remainingDoor;
    }

    private static int getSwitchedDoor(int chosenDoor, int openedDoor) {
        return getOtherDoor(chosenDoor, openedDoor);
    }

    private static DescriptiveStatistics calculateStats(Map<Integer, Result> results) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Result result : results.values()) {
            stats.addValue(result.isWinning() ? 1 : 0);
        }
        return stats;
    }

    private static void printResults(DescriptiveStatistics stats) {
        System.out.println("Количество игр: " + NUMBER_OF_TRIALS);
        System.out.println("Средний процент выигрышей: " + stats.getMean() * 100 + "%");
        System.out.println("Стандартное отклонение: " + stats.getStandardDeviation());
    }

    public static class Result {
        private boolean winning;

        public Result(boolean winning) {
            this.winning = winning;
        }

        public boolean isWinning() {
            return winning;
        }

        public void setWinning(boolean winning) {
            this.winning = winning;
        }
    }
}