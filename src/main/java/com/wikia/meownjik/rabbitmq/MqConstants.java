package com.wikia.meownjik.rabbitmq;

public enum MqConstants {
    LOCALHOST("localhost"),
    QUEUE_NAME("Meownjik");

    private final String value;

    MqConstants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
