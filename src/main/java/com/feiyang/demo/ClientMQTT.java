package com.feiyang.demo;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @Author FeiYang
 * @Date 2020/8/25
 * @Version 1.0
 */
public class ClientMQTT {
    //tcp://MQTT安装的服务器地址：MQTT定义的端口号
    public static final String HOST = "tcp://172.16.44.149:1883";

    //定义一个主题
    private static final String TOPIC = "mtopic";

    //定义mqtt的ID，可以在mqtt服务配置中制定

    private static final String clientid = "client11";


    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "stonegeek";
    private String passWord = "hello";

    private ScheduledExecutorService scheduledExecutorService;

    private void start() {
        try {
            //host为主机名，clientid即连接mqtt的客户端ID，一般以唯一标识符表示
            //MemoryPersistence设置clientid的形式保存，默认以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            //MQTT连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置回调
            client.setCallback(new PushCallback());

            MqttTopic topic = client.getTopic(TOPIC);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            options.setWill(topic, "close".getBytes(), 2, true);

            client.connect(options);

            //订阅消息
            int[] Qos = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, Qos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ClientMQTT clientMQTT = new ClientMQTT();
        clientMQTT.start();
    }
}
