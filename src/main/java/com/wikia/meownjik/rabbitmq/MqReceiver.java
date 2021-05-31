package com.wikia.meownjik.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.wikia.meownjik.jdbc.EcoNewsDao;
import com.wikia.meownjik.jdbc.EcoNewsEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static com.wikia.meownjik.rabbitmq.MqConstants.LOCALHOST;
import static com.wikia.meownjik.rabbitmq.MqConstants.QUEUE_NAME;

public class MqReceiver {
    private final String queueName;

    public MqReceiver(String queueName) {
        this.queueName = queueName;
    }

    public MqReceiver() {
        this.queueName = QUEUE_NAME.getValue();
    }
    
    public void receive() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LOCALHOST.getValue());

        Connection connectionReceiver = factory.newConnection();
        Channel channelReceiver = connectionReceiver.createChannel();

        channelReceiver.queueDeclare(QUEUE_NAME.getValue(), false, false, false, null);
        System.out.println(" [*] Waiting for messages.");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };
        channelReceiver.basicConsume(QUEUE_NAME.getValue(), true, deliverCallback, consumerTag -> { });
    }

    public void receiveNews() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(LOCALHOST.getValue());

        Connection connectionReceiver = factory.newConnection();
        Channel channelReceiver = connectionReceiver.createChannel();

        channelReceiver.queueDeclare(QUEUE_NAME.getValue(), false, false, false, null);
        System.out.println(" [*] Waiting for messages.");

        var dao = new EcoNewsDao();
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            var news = EcoNewsEntity.parseEcoNewsString(message);
            System.out.println("[x] Received news: \n" + news);
            dao.insert(news);
        };
        channelReceiver.basicConsume(QUEUE_NAME.getValue(), true, deliverCallback, consumerTag -> { });
    }
}
