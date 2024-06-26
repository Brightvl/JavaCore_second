package ru.gb.developmentKit.lesson6_build.hw6_montyHall;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * В качестве задачи предлагаю вам реализовать код для демонстрации парадокса
 * Монти Холла и наглядно убедиться в верности парадокса
 * (запустить игру в цикле на 1000 и вывести итоговый счет).
 * Необходимо:
 * Создать свой Java Maven или Gradle проект;
 * Подключить зависимость lombok.
 * Можно подумать о подключении какой-нибудь математической библиотеки для работы со статистикой
 * Самостоятельно реализовать прикладную задачу;
 * Сохранить результат в HashMap<шаг теста, результат>.
 * Вывести на экран статистику по победам и поражениям
 */
class Main {
    public static void main(String[] args) {
        MontyHallGame montyHallGame = new MontyHallGame();
        montyHallGame.simulateGames();
        montyHallGame.printResults();
    }
}

/**
 * Парадокс Монти-Холла
 */
public class MontyHallGame {

    public static final int NUM_TESTS = 1000;
    @Getter
    private Map<Integer, Boolean> results;
    private final Random random;

    public MontyHallGame() {
        this.results = new HashMap<>();
        this.random = new Random();
    }

    /**
     * Метод имитирует проведение каждой игры. В цикле он создает сценарии для каждой игры.
     */
    public void simulateGames() {
        for (int i = 1; i <= NUM_TESTS; i++) {
            // выбор двери игроком
            int chosenDoor = random.nextInt(3) + 1;
            // Расположение автомобиля (случайным образом).
            int carBehind = random.nextInt(3) + 1;
            // Ведущий открывает дверь, исключая выбор игрока и место, где находится автомобиль
            int doorOpenedByHost = getDoorOpenedByHost(chosenDoor, carBehind);
            // Игрок решает изменить выбор или оставить его
            boolean switchDoor = random.nextBoolean();
            // Выигрыш или проигрыш
            boolean win = switchDoor == (chosenDoor != carBehind);
            results.put(i, win);
        }
    }

    /**
     * Метод для действий ведущего, который открывает дверь, исключая выбор игрока и дверь с автомобилем.
     *
     * @param chosenDoor выбранная игроком дверь
     * @param carBehind  расположение автомобиля
     * @return номер двери
     */
    private int getDoorOpenedByHost(int chosenDoor, int carBehind) {
        int doorOpenedByHost;
        do {
            doorOpenedByHost = random.nextInt(3) + 1;
        } while (doorOpenedByHost == chosenDoor || doorOpenedByHost == carBehind);
        return doorOpenedByHost;
    }

    /**
     * Метод для подсчета и вывода статистики по победам и поражениям.
     */
    public void printResults() {
        long wins = results.values().stream().filter(win -> win).count();
        long losses = NUM_TESTS - wins;
        System.out.println("Из 1000 игр:");
        System.out.println("Побед: " + wins);
        System.out.println("Поражений: " + losses);
    }
}

