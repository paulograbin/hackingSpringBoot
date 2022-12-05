package com.greglturnquist.hackingspringboot.classic;

import com.greglturnquist.hackingspringboot.classic.models.Cart;
import com.greglturnquist.hackingspringboot.classic.models.CartItem;
import com.greglturnquist.hackingspringboot.classic.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.classic.repositories.ItemRepository;
import org.springframework.stereotype.Service;


@Service
class CartService {

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    Cart addToCart(String cartId, Integer id) {

        Cart cart = this.cartRepository.findById(cartId)
                .orElseGet(() -> new Cart("My Cart"));

        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(id))
                .findAny()
                .map(cartItem -> {
                    cartItem.increment();
                    return cart;
                })
                .orElseGet(() -> {
                    this.itemRepository.findById(id)
                            .map(CartItem::new)
                            .map(cartItem -> {
                                cart.getCartItems().add(cartItem);
                                return cart;
                            })
                            .orElseGet(() -> cart);
                    return cart;
                });

        return this.cartRepository.save(cart);
    }
}
