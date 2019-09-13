package com.kaisn.utils.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MyPublisher {

    public static void publishTopic()
    {
        try
        {
            //创建连接工厂
            TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.109.128:61616");
            //创建连接
            TopicConnection connection = connectionFactory.createTopicConnection();
            //启动连接
            connection.start();
            //创建会话
            TopicSession topicSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建topic
            Topic topic = topicSession.createTopic("topic-emp-web-test");
            //创建发布者
            TopicPublisher publisher = topicSession.createPublisher(topic);
            //创建消息
            TextMessage textMessage = topicSession.createTextMessage("发布第一条消息！");
            //发布消息
            publisher.publish(textMessage);
            topicSession.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        publishTopic();
    }
}
