package at.michaelaltenburger.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class EmbeddedRabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmbeddedRabbitMQApplication.class, args);
    }

}