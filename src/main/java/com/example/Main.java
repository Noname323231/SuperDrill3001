package com.example;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class Main
{
    public static void main(String[] args)
    {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.print("Среда разработки готова\nСостав команды: Титов А.А. , Шафеев Р.Р.\nНазвание команды: 1000 и 1 костыль");
    }
}
