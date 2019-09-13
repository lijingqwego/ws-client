package com.kaisn.utils.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 主题订阅者
 */
public class MySubscriber implements Runnable
{


    public void run()
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
            //创建订阅者
            TopicSubscriber subscriber = topicSession.createSubscriber(topic);
            //接收消息
            Message message = subscriber.receive();
            if(message != null)
            {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("订阅的内容是："+textMessage.getText());
            }
            topicSession.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        //使用多个线程模拟多个订阅者
        MySubscriber mySubscriber = new MySubscriber();
        Thread t1 = new Thread(mySubscriber);
        Thread t2 = new Thread(mySubscriber);
        t1.start();
        t2.start();
    }
}
