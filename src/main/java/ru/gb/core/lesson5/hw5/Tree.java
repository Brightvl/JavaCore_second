package ru.gb.core.lesson5.hw5;

import java.io.File;

public class Tree {

    public static void main(String[] args) {
        print(new File("src/main/java"), "", true);
    }

    /**
     * Метод выводит в консоль древо директорий вглубь относительно указанного файла
     * @param file файл
     * @param indent строка отступа
     * @param isLast является ли текущая директория последней в списке родительской директории
     */
    static void print(File file, String indent, boolean isLast) {
        System.out.print(indent);
        if (isLast) {
            System.out.print("└─");
            indent += "  ";
        } else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        File[] files = file.listFiles();
        if (files == null)
            return;

        for (int i = 0; i < files.length; i++) {
            print(files[i], indent, i == files.length - 1);
        }
    }
}