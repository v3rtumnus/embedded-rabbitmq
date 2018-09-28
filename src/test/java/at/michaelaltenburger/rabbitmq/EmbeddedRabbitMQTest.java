package at.michaelaltenburger.rabbitmq;

import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMq;
import io.arivera.oss.embedded.rabbitmq.EmbeddedRabbitMqConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmbeddedRabbitMQTest {

    private static EmbeddedRabbitMq rabbitMq;

    @LocalServerPort
    private int port;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.test}")
    private String testQueue;

    @BeforeAll
    public static void setUpRabbitMq() {
        EmbeddedRabbitMqConfig config = new EmbeddedRabbitMqConfig.Builder()
                .erlangCheckTimeoutInMillis(30000)
                .rabbitMqServerInitializationTimeoutInMillis(30000)
                .defaultRabbitMqCtlTimeoutInMillis(30000)
                .build();
        rabbitMq = new EmbeddedRabbitMq(config);
        rabbitMq.start();
    }

    @AfterAll
    public static void shutdownRabbitMq() {
        rabbitMq.stop();
    }


    @Test
    public void sendDataShouldSendMessageToRabbit() {
        new RestTemplate().postForLocation("http://localhost:" + port, "testData");

        assertEquals("testData", rabbitTemplate.receiveAndConvert(testQueue, 1000));
    }
}