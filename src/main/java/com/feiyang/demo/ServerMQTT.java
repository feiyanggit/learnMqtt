package com.feiyang.demo;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * @Author FeiYang
 * @Date 2020/8/25
 * @Version 1.0
 */
public class ServerMQTT {

    //tcp://MQTT安装的服务器地址：MQTT定义的端口号
    public static final String HOST = "tcp://172.16.44.149:1883";

    //定义一个主题
    private static final String TOPIC = "mtopic";

    //定义mqtt的ID，可以在mqtt服务配置中制定

    private static final String clientid = "server11";

    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "stonegeek";
    private String passWord = "hello";

    private MqttMessage message;

    public ServerMQTT() throws MqttException {
        client = new MqttClient(HOST,clientid,new MemoryPersistence());
        connect();
    }

    private void connect(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        //设置超时时间
        options.setConnectionTimeout(10);
        //设置会话心跳时间
        options.setKeepAliveInterval(20);


        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic11 = client.getTopic(TOPIC);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

    public void publish(MqttTopic topic,MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely!" + token.isComplete());
    }

    public static void main(String[] args) throws MqttException {
        ServerMQTT serverMQTT = new ServerMQTT();
        serverMQTT.message = new MqttMessage();
        serverMQTT.message.setQos(1);
        serverMQTT.message.setRetained(true);
        serverMQTT.message.setPayload("hello topic11".getBytes());
        serverMQTT.publish(serverMQTT.topic11,serverMQTT.message);

        System.out.println(serverMQTT.message.isRetained() + "------ratained状态");
    }
}
