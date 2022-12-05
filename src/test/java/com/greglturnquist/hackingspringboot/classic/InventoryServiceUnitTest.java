package com.greglturnquist.hackingspringboot.classic;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@Disabled
class InventoryServiceUnitTest {

    // tag::class-under-test[]
    InventoryService inventoryService;

    @MockBean private ItemRepository itemRepository;

    @MockBean private CartRepository cartRepository;
    // end::class-under-test[]

    @BeforeEach
    void setUp() {
        // Define test data
        Item sampleItem = new Item(1, "TV tray", "Alf TV tray", 19.99);
        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart", Collections.singletonList(sampleCartItem));
        sampleCartItem.setCart(sampleCart);

        // Define mock interactions provided
        // by your collaborators
        when(cartRepository.findById(anyString())).thenReturn(Optional.empty());
        when(itemRepository.findById(anyInt())).thenReturn(Optional.of(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(sampleCart);

        inventoryService = new InventoryService(itemRepository, cartRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem() {
        Cart cart = inventoryService.addItemToCart("My Cart", 1);

        assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                .containsExactlyInAnyOrder(1);

        assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                .containsExactly(new Item(1, "TV tray", "Alf TV tray", 19.99));
    }
}