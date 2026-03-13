package com.miguel.assistencesystem.domain.valueobjects;

public final class ServiceOrderProtocolGenerator {

    private ServiceOrderProtocolGenerator() {}

    public static String generate() {
        return "SO-" + System.currentTimeMillis();
    }
}