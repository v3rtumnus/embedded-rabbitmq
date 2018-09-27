package at.riag.scanpay.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class RabbitMqTestConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;
    
    @Value("${queue.test}")
    private String testQueue;

    @Value("${routing.test}")
    private String testRoutingKey;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    @Bean
    Queue testQueue() {
        return new Queue(testQueue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    List<Binding> bindings() {
        return Collections.singletonList(BindingBuilder.bind(testQueue()).to(exchange()).with(testRoutingKey));
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(username);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }
}
