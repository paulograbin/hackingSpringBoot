package com.greglturnquist.hackingspringboot.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
public class SpringAmqpItemController {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpItemController.class);

    private final AmqpTemplate template;

    public SpringAmqpItemController(AmqpTemplate template) {
        this.template = template;
    }

    @PostMapping("/items")
    ResponseEntity<Object> addnewItemUsingAmqp(@RequestBody Item item) {
        this.template.convertAndSend("hacking-spring-boot", "new-items-spring-amqp", item);

        return ResponseEntity.created(URI.create("/items")).build();
    }
}
