package ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.field;


import ru.gb.core.lesson2_ticTacToe.Hw2_ticTacToe.ui.Colorant;

public class GameField {

    Colorant colorant;

    private final char DOT_EMPTY = '*';

    private int fieldSizeX;
    private int fieldSizeY;

    private char[][] field;


    public GameField(int widthField, int heightFields) {
        this.fieldSizeX = widthField;
        this.fieldSizeY = heightFields;
        this.colorant = new Colorant();

        this.field = new char[fieldSizeY][fieldSizeX];

    }

    /**
     * Инициализация игрового поля
     */
    public void initialize() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    public void printField() {
        System.out.print("+ ");
        for (int i = 0; i < fieldSizeY; i++) {
            System.out.print(colorant.stringColor(33, " " + (i + 1)));
        }
        System.out.println(" ↔ X");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print(colorant.stringColor(32, i + 1) + " |");
            for (int j = 0; j < fieldSizeY; j++) {
                if (field[i][j] == '0') {
                    System.out.print(colorant.stringColor(34, field[i][j]) + "|");
                } else if (field[i][j] == 'X') {
                    System.out.print(colorant.stringColor(31, field[i][j]) + "|");
                } else {
                    System.out.print(field[i][j] + "|");
                }
            }
            System.out.println();
        }
        System.out.println("↕ Y");
        System.out.println("____________");
    }


    /**
     * Проверка, является ли ячейка игрового поля пустой
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isCellEmpty(int x, int y) {
        return field[y][x] == DOT_EMPTY;
    }


    /**
     * Проверка доступности ячейки игрового поля (не вышли за пределы)
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка, является ли ячейка игрового поля искомым символом
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isCellDot(int x, int y, char dot) {
        return field[y][x] == dot;
    }

    public void fillTurn(int y, int x, char dot) {
        this.field[y][x] = dot;
    }

    public int getFieldSizeX() {
        return fieldSizeX;
    }

    public int getFieldSizeY() {
        return fieldSizeY;
    }

    public char[][] getField() {
        return field;
    }


}
