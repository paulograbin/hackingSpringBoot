package com.greglturnquist.hackingspringboot.classic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@WebMvcTest(HomeController.class) // <1>
@Disabled
public class HomeControllerSliceTest {

    private WebTestClient client;

    @MockBean // <2>
    InventoryService inventoryService;

    @BeforeEach
    void setUp(@Autowired MockMvc mockMvc) {
        this.client = MockMvcWebTestClient.bindTo(mockMvc).build(); // <3>
    }

    @Test
    void homePage() {
        when(inventoryService.getInventory()).thenReturn(Arrays.asList( //
                new Item(1, "name1", "desc1", 1.99), //
                new Item(2, "name2", "desc2", 9.99) //
        ));
        when(inventoryService.getCart("My Cart")) //
                .thenReturn(Optional.of(new Cart("My Cart")));

        client.get().uri("/").exchange() //
                .expectStatus().isOk() //
                .expectBody(String.class) //
                .consumeWith(exchangeResult -> {
                    assertThat( //
                            exchangeResult.getResponseBody()).contains("action=\"/add/1\"");
                    assertThat( //
                            exchangeResult.getResponseBody()).contains("action=\"/add/2\"");
                });
    }
}
// end::code[]