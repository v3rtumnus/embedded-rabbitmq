package at.michaelaltenburger.rabbitmq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TestController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final RabbitTemplate rabbitTemplate;

    @Value("${routing.test}")
    private String testRoutingKey;

    @Value("${spring.rabbitmq.template.exchange}")
    private String exchangeName;

    @Autowired
    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void sendData(@Valid @RequestBody String data) {
        log.trace("Got request to send data");

        this.rabbitTemplate.convertAndSend(exchangeName, testRoutingKey, data);
    }
}
