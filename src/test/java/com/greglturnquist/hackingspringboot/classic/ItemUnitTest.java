package com.greglturnquist.hackingspringboot.classic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@Disabled
class ItemUnitTest {

    @Test // <1>
    void itemBasicsShouldWork() {
        Item sampleItem = new Item(1, "TV tray", "Alf TV tray", 19.99); // <2>

        // Test various aspects using AssertJ <3>
        assertThat(sampleItem.getId()).isEqualTo(1);
        assertThat(sampleItem.getName()).isEqualTo("TV tray");
        assertThat(sampleItem.getDescription()).isEqualTo("Alf TV tray");
        assertThat(sampleItem.getPrice()).isEqualTo(19.99);

        assertThat(sampleItem.toString()).isEqualTo( //
                "Item{id='1', name='TV tray', description='Alf TV tray', price=19.99}");

        Item sampleItem2 = new Item(1, "TV tray", "Alf TV tray", 19.99);
        assertThat(sampleItem).isEqualTo(sampleItem2);
    }
}
// end::code[]