package com.greglturnquist.hackingspringboot.classic;

import com.greglturnquist.hackingspringboot.classic.models.Item;
import com.greglturnquist.hackingspringboot.classic.repositories.ItemRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Disabled
public class JpaSliceTest {

    @Autowired
    ItemRepository repository;

    @Test
    void itemRepositorySavesItems() {
        Item sampleItem = new Item(
                "name", "description", 1.99);

        Item savedItem = repository.save(sampleItem);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("name");
        assertThat(savedItem.getDescription()).isEqualTo("description");
        assertThat(savedItem.getPrice()).isEqualTo(1.99);
    }
}
