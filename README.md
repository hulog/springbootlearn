## 简介 
基于springboot框架，学习各种中间件如redis/rabbitmq/okhttp3等。 
## 服务主体
1. 爬取代理网站ip，实现动态验证IP可用性，将可用IP发送到RabbitMQ，供多个投票服务消费。