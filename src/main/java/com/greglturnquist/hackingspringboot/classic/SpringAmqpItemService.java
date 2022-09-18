package com.greglturnquist.hackingspringboot.classic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class SpringAmqpItemService {

    private static final Logger log = LoggerFactory.getLogger(SpringAmqpItemService.class);

    private final ItemRepository repository;

    public SpringAmqpItemService(ItemRepository repository) {
        this.repository = repository;
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(
                            value = "hacking-spring-boot",
                            type = ExchangeTypes.TOPIC),
                    key = "new-items-spring-amqp")
    )
    public void processnewItemsViaSpringAmqp(Item item) {
        log.debug("Consuming => " + item);
        this.repository.save(item);
    }
}
