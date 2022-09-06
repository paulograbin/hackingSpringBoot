package com.greglturnquist.hackingspringboot.classic;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ApiItemController {

    private final ItemRepository repository;

    private final InventoryService service;

    public ApiItemController(ItemRepository repository, InventoryService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/api/carts")
    Iterable<Cart> findAll() {
        Iterable<Cart> carts = this.service.getAllCarts();

        if (carts.iterator().hasNext()) {
            return carts;
        } else {
            return Arrays.asList(this.service.newCart());
        }
    }

    @GetMapping("/api/carts/{id}")
    Cart findOne(@PathVariable String id) {
        return this.service.getCart(id) //
                .orElseThrow(() -> new IllegalStateException("Could find cart " + id));
    }

    @PostMapping("/api/carts/{cartId}/add/{itemId}")
    Cart addToCart(@PathVariable String cartId, @PathVariable Integer itemId) {
        return this.service.addItemToCart(cartId, itemId);
    }

    @DeleteMapping("/api/carts/{cartId}/remove/{itemId}")
    Cart removeFromCart(@PathVariable String cartId, @PathVariable Integer itemId) {
        return this.service.removeOneFromCart(cartId, itemId);
    }
}
