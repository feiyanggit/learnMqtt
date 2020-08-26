参照https://www.cnblogs.com/sxkgeek/p/9140180.html学习

环境 ubuntu、mosquitto

apt-get安装mqtt相关包
1. sudo apt-get install mosquitto
2. sudo apt-get install mosquitto-clients

测试mosquitto是否正确运行
3. sudo service mosquitto status

修改配置文件
4. sudo vi /etc/mosquitto/mosquitto.conf
加入以下内容
allow_anonymous false
password_file /etc/mosquitto/pwfile
acl_file /etc/mosquitto/acl

port 1883
protocol mqtt

listener 9001
protocol websockets

添加用户信息
5. sudo mosquitto_passwd -c /etc/mosquitto/pwfile stonegeek
输入密码 本例为 hello

添加主题Topic和用户的关系
6. sudo vi /etc/mosquitto/acl
user stonegeek
topic write mtopic/#

user stonegeek
topic read mtopic/#


启动
7. mosquitto -c /etc/mosquitto/mosquitto.conf

停止
8. ps -aux|grep mosquitto
9. sudo kill -9 pid

订阅端启动
10. mosquitto_sub -h 172.16.44.149 -t mtopic -u stonegeek -P hello

发布端启动
11. mosquitto_pub -h 172.16.44.146 -t mtopic -u stonegeek -P hello -m "yourenma"

以上是在mqtt服务器上用命令行测试

java程序之间的发布订阅在ServerMQTT和ClientMQTT类中

index.html是前端html页面订阅代码

以上初步学习结果







