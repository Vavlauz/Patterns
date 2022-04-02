package ru.netology.domain;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {

    }

    private static Random random = new Random();


    public static RequestInfo generateRequestCard(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return new RequestInfo(generateCity(),
                faker.name().lastName() + " " + faker.name().firstName(),
                faker.phoneNumber().phoneNumber());
    }

    public static String generateCity() {
        String[] cities = new String[]{"Новосибирск", "Барнаул", "Санкт-Петербург", "Москва", "Ставрополь", "Краснодар"};
        return cities[random.nextInt(cities.length)];
    }

    public static String generateDate(int shift, int range) {
        return LocalDate.now().plusDays(3 + shift).plusDays(random.nextInt(range))
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}

