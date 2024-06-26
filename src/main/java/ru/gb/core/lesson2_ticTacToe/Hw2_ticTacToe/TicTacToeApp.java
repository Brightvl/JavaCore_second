package ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe;


import ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.ui.GameProcess;
/* ТЗ
1. Полностью разобраться с кодом программы разработанной на семинаре, переписать программу.
Это минимальная задача для сдачи домашней работы.

Усложняем задачу:

2.* Переработать метод проверки победы, логика проверки победы должна работать для поля 5х5 и
количества фишек 4. Очень желательно не делать это просто набором условий для каждой из
возможных ситуаций! Используйте вспомогательные методы, используйте циклы!

3.**** Доработать искусственный интеллект, чтобы он мог блокировать ходы игрока.
 */

/**
 * Класс запускающий игру в Крестики нолики
 */
public class TicTacToeApp {

    /**
     * Метод для запуска игры.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        GameProcess gameUi = new GameProcess(5, 5, 4);
        gameUi.runGame();

    }
}
