package com.greglturnquist.hackingspringboot.classic;

import com.greglturnquist.hackingspringboot.classic.models.Cart;
import com.greglturnquist.hackingspringboot.classic.models.CartItem;
import com.greglturnquist.hackingspringboot.classic.models.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


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