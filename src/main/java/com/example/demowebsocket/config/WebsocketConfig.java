package com.example.demowebsocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
public class WebsocketConfig extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

    @Value("${spring.rabbitmq.host}")
    private String relayHost;

    @Value("${spring.rabbitmq.port}")
    private Integer relayPort;

    @Value("${spring.rabbitmq.username}")
    private String relayUsername;

    @Value("${spring.rabbitmq.password}")
    private String relayPassword;

    protected void configureStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }

    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        Thông qua exchange thì message trong queue sẽ tự động auto-delete,
//        còn nếu message được bắn qua queue mà ko được set header {'auto-delete': 'true'} thì nó sẽ không tự động xóa
        registry.enableStompBrokerRelay("/exchange/", "/queue/", "/topic/")
                .setUserDestinationBroadcast("/topic/unresolved.user.dest")
                .setUserRegistryBroadcast("/topic/registry.broadcast")
                .setRelayHost(relayHost)
                .setRelayPort(relayPort)
                .setClientLogin(relayUsername)
                .setClientPasscode(relayPassword)
                .setSystemLogin(relayUsername)
                .setSystemPasscode(relayPassword);

        registry.setApplicationDestinationPrefixes("/chatroom");
    }

}
