package com.wikia.meownjik.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class RabbitMqTest {
    private final static String QUEUE_NAME = "hello";

    @Test
    public void helloWorld() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        //Receiver
        Connection connectionReceiver = factory.newConnection();
        Channel channelReceiver = connectionReceiver.createChannel();

        channelReceiver.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channelReceiver.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        //Sender
        for (var message : new String[]{"Hello World!", "Test"}) {
            try (Connection connectionSender = factory.newConnection();
                 Channel channelSender = connectionSender.createChannel()) {
                channelSender.queueDeclare(QUEUE_NAME, false, false, false, null);
                channelSender.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }

    @Test
    public void senderAndReceiverTest() throws IOException, TimeoutException {
        var receiver = new MqReceiver();
        receiver.receive();
        var sender = new MqSender();
        for (var message : new String[]{"Hello World!", "Test"}) {
            sender.send(message);
        }
    }
}
