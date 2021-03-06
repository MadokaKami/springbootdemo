package com.funnytree.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description WebSocket配置累
 * @ClassName WebSocketConfig
 * @author 李英夫
 * @since 2018/10/7 20:50
 * @version V1.0.0
 * @Copyright (c) All Rights Reserved, 2018.
 * @see WebSocketMessageBrokerStats 连接信息看这个
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    //MessageBroker-n线程
    //通过WebSocketMessageBrokerStats查看连接信息 通过WebSocketMessageBrokerStats.toString

    @Autowired
    private WebSocketMessageBrokerStats webSocketMessageBrokerStats;

    /**
     * 可以在这里配置handler
     * 端点的作用：客户端在订阅或发布消息 到目的地址前，要连接该端点
     * @param registry stomp服务注册
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //拦截器还没有配
        registry.addEndpoint("/webSocketServer").addInterceptors()
                /*.setHandshakeHandler(new DefaultHandshakeHandler())*/.setAllowedOrigins("*");
        //registry.addEndpoint("/webSocketJsServer").setAllowedOrigins("*").withSockJS();
    }

    /**
     * @param registry 消息代理注册
     * 下面着重说一下simpMessageBroker的心跳机制
     * STOMP规范:接收器应该考虑误差范围，所以这里超时三倍之后才会真正断开连接，stompJs设置为2倍
     * 之前的猜测是错误的，SimpleBrokerMessageHandler关于心跳部分存在BUG，{@code lastWriteTime}这里没有及时更新，线程每次启动都会发送心跳
     * {@link org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler#startInternal()}，发送心跳的时间完全取决于线程池中线程的延迟
     * @see SimpleBrokerMessageHandler.SessionInfo#SessionInfo 这里设置了心跳时长
     * @see SimpleBrokerMessageHandler.HeartbeatTask#run() 这里设置了超时断开
     * @see this#wssHeartbeatThreadPool() 线程池配置
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /*
         * 简单消息代理器，以内存做消息代理
         * 这里如果不重载的话，会默认配置topic路径，函数重写之后会代理topic和queue前缀的连接
         * 应用程序的目的地 以 "/app" 为前缀，而代理的目的地以 "/topic" 和 "/queue" 作为前缀；
         * js.url = "/webSocketServer/app/helloWord" 监听 @MessageMapping("/helloWord") 注释的方法.
         */
        //订阅路径
        registry.enableSimpleBroker("/queue","/topic")
             //第一个数字表示服务器向客户端发送心跳的频率，第二个数字是客户端应该多久发送一次心跳，超出接收间隔连接失效(心跳需配合setTaskScheduler才能生效)
             //参数一以服务器端为准，参数二取服务器端参数二与stompJs端outgoing参数的最大值

            .setHeartbeatValue(new long[]{12000,6000})
             //设入线程池
            .setTaskScheduler(wssHeartbeatThreadPool());

        //全局订阅消息前缀
        registry.setApplicationDestinationPrefixes("/app");
        //点对点消息前缀
        registry.setUserDestinationPrefix("/user");
        //将消息委托给MQ进行代理
        //默认情况下：STOMP代理中继会假设监听localhost:61613,client的username和password均为guest
        /*registry.enableStompBrokerRelay("/queue","/topic")
                .setRelayHost("localhost")
                .setRelayPort(88888)
                .setClientLogin("username")
                .setClientPasscode("password")
                .setSystemHeartbeatReceiveInterval(2000) // 设置心跳信息接收时间间隔
                .setSystemHeartbeatSendInterval(2000); // 设置心跳信息发送时间间隔*/
    }

    /**
     * 消息传输参数配置
     * @param registry 消息传输注册
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.setMessageSizeLimit(4 * 1024 * 1024) //设置消息字节数大小，默认16K，这里设为4M
                .setSendBufferSizeLimit(128 * 1024 * 1024)//设置消息缓存大小，默认512K，这里设为128M
                .setSendTimeLimit(120000); //设置消息发送时间限制毫秒
    }

    /**
     * 输入通道参数设置
     * 可以在这里配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4) //设置消息输入通道的线程池线程数(应该设置为CPU数量)
                .maxPoolSize(Integer.MAX_VALUE)//最大线程数
                .keepAliveSeconds(60);//线程活动时间
        registration.interceptors(new ChannelInterceptor(){
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("输入前");
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                log.info("执行输入");
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                log.info("输入成功");
            }
        });
    }


    /**
     * 输出通道参数设置
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(4).maxPoolSize(Integer.MAX_VALUE);
        registration.interceptors(new ChannelInterceptor(){
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("输出前");
                return message;
            }

            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                log.info("执行输出");
            }

            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                log.info("输出成功");
            }
        });
    }

    @EventListener
    public void onSessionDisconnectEvent (SessionDisconnectEvent event){
        System.out.println(event.toString());
    }

    /**
     * webSocket 线程池
     */
    @Bean(destroyMethod = "destroy")
    public ThreadPoolTaskScheduler wssHeartbeatThreadPool(){
        //线程池配置
        //对应的解决方法的网址：https://stackoverflow.com/questions/39220647/spring-stomp-over-websockets-not-scheduling-heartbeats
        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();
        return te;
    }
}
