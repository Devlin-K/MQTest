package com.biz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * Created by Narsissist on 2017/12/4.
 */
public class Send {
    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {

        ConnectionFactory mqfactory = new ConnectionFactory();
        //定义Rabbitmq 地址，端口，用户名，密码
        mqfactory.setHost("39.108.232.236");
        mqfactory.setPort(5672);
        mqfactory.setUsername("Kim");
        mqfactory.setPassword("Kim");

        //创建连接
        Connection connection = mqfactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //获取输入的信息，“q”为退出，其他之设置为Username
        String temp;
        Scanner scan=new Scanner(System.in);
        UserM userMessage = new UserM();
        while (true) {
            System.out.println("Type  q to exit");
            System.out.println("you need a name：");
            temp=scan.next();
            //判断输入
            if ("q".equals(temp)) {
                System.out.println("You are Exit!");
                return;
            }else {
                System.out.println("Let's begin chat");
                userMessage.setName(temp);
                System.out.println("hello" + "  " + userMessage.getName() + "  " + "you can chat now,enjoy it");
                userMessage.setMessage(scan.next());
                String msg = userMessage.getName() + "   " + "said" + "   " + userMessage.getMessage();
                channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
                System.out.println(msg);
                //关闭连接
            }

            //关闭所有连接
            scan.close();
            channel.close();
            connection.close();

        }
    }
}