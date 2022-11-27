package com.loveable.tracker.utils;

import com.github.javafaker.Faker;

public class UuidGenerator {
    public static String uuid() {
        Faker faker = new Faker();
        return faker.random().hex();
    }
}
