package com.wikia.meownjik.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.wikia.meownjik.rabbitmq.MqConstants.*;

public class MqSender {
    private final String queueName;

    public MqSender(String queueName) {
        this.queueName = queueName;
    }

    public MqSender() {
        this.queueName = QUEUE_NAME.getValue();
    }

    public void send(String message) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LOCALHOST.getValue());
        try (Connection connectionSender = factory.newConnection();
             Channel channelSender = connectionSender.createChannel()) {
            channelSender.queueDeclare(queueName, false, false, false, null);
            channelSender.basicPublish("", queueName, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
