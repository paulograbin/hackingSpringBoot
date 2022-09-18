package com.greglturnquist.hackingspringboot.classic;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class RabbitTest {

    @Container static RabbitMQContainer container = new RabbitMQContainer(DockerImageName.parse("rabbitmq").withTag("3.7.25-magement-alpine"));

    WebTestClient webTestClient;

    @Autowired
    ItemRepository repository;


    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", container::getContainerIpAddress);
        registry.add("spring.rabbitmq.port", container::getAmqpPort);
    }

    @BeforeEach
    void setUp(@Autowired MockMvc mockMvc) {
        this.webTestClient = MockMvcWebTestClient.bindTo(mockMvc).build();
    }

    @Test
    void verifyMessagingThroughAmqp() throws InterruptedException {
        this.webTestClient.post().uri("/items")
                .bodyValue(new Item("Alf alarm clock", "nothing improtant", 19.99))
                .exchange()
                .expectStatus().isCreated()
                .expectBody();

        Thread.sleep(1500L);

        this.webTestClient.post().uri("/items")
                .bodyValue(new Item("Alf alarm clock", "nothing improtant", 29.00))
                .exchange()
                .expectStatus().isCreated()
                .expectBody();

        Thread.sleep(2000L);

        Iterable<Item> items = this.repository.findAll();

        assertThat(items).flatExtracting(Item::getName).containsExactly("Alf alarm clock", "Smurf TV tray");
        assertThat(items).flatExtracting(Item::getDescription).containsExactly("nothing improtant", "nothing improtant");
        assertThat(items).flatExtracting(Item::getPrice).containsExactly(19.99, 29.99);
    }
}
