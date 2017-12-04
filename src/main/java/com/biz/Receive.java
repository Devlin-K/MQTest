package com.biz;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Narsissist on 2017/12/4.
 */
public class Receive {

    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws java.io.IOException, java.lang.InterruptedException, TimeoutException {

        ConnectionFactory mqfactory = new ConnectionFactory();
        mqfactory.setHost("39.108.232.236");
        mqfactory.setPort(5672);
        mqfactory.setUsername("Kim");
        mqfactory.setPassword("Kim");
        Connection connection = mqfactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        Consumer consumer = new DefaultConsumer(channel) {

            UserM userMessage =new UserM();

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(  message );
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }



}
