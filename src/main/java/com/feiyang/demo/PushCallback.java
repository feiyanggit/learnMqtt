package com.feiyang.demo;

/**
 * @Author FeiYang
 * @Date 2020/5/23
 * @Version 1.0
 */

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.LocalDateTime;

/**
 * 发布消息的回调类
 * <p>
 * <p>
 * <p>
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * <p>
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * <p>
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * <p>
 * 必须在回调类中实现三个方法：
 * <p>
 * <p>
 * <p>
 * public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 * <p>
 * <p>
 * <p>
 * public void connectionLost(Throwable cause)在断开连接时调用。
 * <p>
 * <p>
 * <p>
 * public void deliveryComplete(MqttDeliveryToken token))
 * <p>
 * 接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 * <p>
 * 由 MqttClient.connect 激活此回调。
 */

public class PushCallback implements MqttCallback {

    @Override

    public void connectionLost(Throwable throwable) {

        System.out.println("连接断开！");

        System.out.println(LocalDateTime.now());

    }

    @Override

    public void messageArrived(String topic, MqttMessage message) throws Exception {

        System.out.println("接收消息主题 : " + topic);

        System.out.println("接收消息Qos : " + message.getQos());

        System.out.println("接收消息内容 : " + new String(message.getPayload()));

    }

    @Override

    public void deliveryComplete(IMqttDeliveryToken token) {

        System.out.println("分发完成---------" + token.isComplete());

        System.out.println(LocalDateTime.now()+"\r\n");

    }

}
