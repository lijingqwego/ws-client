package com.kaisn.utils.mq;


import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.Scanner;

public class MyProducer {

    //默认连接用户名
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    //默认连接密码
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    //默认连接地址
    private static final String BROKER_URL = "tcp://192.168.109.128:61616";


    /**
     * 发送消息
     */
    public static void sendToMQ(String content) {
        //连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKER_URL);
        try {
            //连接
            Connection connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            //消息目的地
            Destination destination = session.createQueue("emp-web-mq-queue");
            //消息生产者
            MessageProducer producer = session.createProducer(destination);
            //设置不持久化，此处学习，实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //创建一条文本消息
            TextMessage message = session.createTextMessage(content);
            //生产者发送消息
            producer.send(message);
            session.commit();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的姓名：");
        String name = sc.nextLine();
        System.out.println("请输入你的年龄：");
        int age = sc.nextInt();
        System.out.println("请输入你的工资：");
        float salary = sc.nextFloat();
        sendToMQ("\n姓名："+name+"\t"+"年龄："+age+"\t"+"工资："+salary);
    }
}