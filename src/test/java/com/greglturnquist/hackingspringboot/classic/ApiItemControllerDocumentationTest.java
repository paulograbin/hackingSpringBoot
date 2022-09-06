package com.greglturnquist.hackingspringboot.classic;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;


@WebMvcTest(controllers = ApiItemController.class)
@AutoConfigureRestDocs
public class ApiItemControllerDocumentationTest {

    private WebTestClient webTestClient;

    @MockBean
    InventoryService service;
    @MockBean
    ItemRepository repository;

    @BeforeEach
    void setUp(@Autowired MockMvc mockMvc, @Autowired RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = MockMvcWebTestClient.bindTo(mockMvc)
                .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void findingAllItems() {
        when(repository.findAll()).thenReturn( // <1>
                Arrays.asList(new Item(1, "Alf alarm clock", //
                        "nothing I really need", 19.99)));

        this.webTestClient.get().uri("/api/items") //
                .exchange() //
                .expectStatus().isOk() //
                .expectBody() //
                .consumeWith(document("findAll", preprocessResponse(prettyPrint()))); // <2>
    }

    @Test
    void postNewItem() {
        when(repository.save(any())).thenReturn( //
                new Item(1, "Alf alarm clock", "nothing important", 19.99));

        this.webTestClient.post().uri("/api/items") // <1>
                .bodyValue(new Item("Alf alarm clock", "nothing important", 19.99)) // <2>
                .exchange() //
                .expectStatus().isCreated() // <3>
                .expectBody(Item.class) //
                .consumeWith(document("post-new-item", preprocessResponse(prettyPrint()))); // <4>
    }

    @Test
    void findOneItem() {
        when(repository.findById(1)).thenReturn( //
                Optional.of(new Item(1, "Alf alarm clock", "nothing I really need", 19.99))); // <1>

        this.webTestClient.get().uri("/api/items/1") //
                .exchange() //
                .expectStatus().isOk() //
                .expectBody() //
                .consumeWith(document("findOne", preprocessResponse(prettyPrint()))); // <2>
    }
}
