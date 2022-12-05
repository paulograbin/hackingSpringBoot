package com.greglturnquist.hackingspringboot.classic;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.greglturnquist.hackingspringboot.classic.models.Cart;
import com.greglturnquist.hackingspringboot.classic.models.CartItem;
import com.greglturnquist.hackingspringboot.classic.models.Item;
import com.greglturnquist.hackingspringboot.classic.repositories.CartRepository;
import com.greglturnquist.hackingspringboot.classic.repositories.ItemRepository;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

    private ItemRepository itemRepository;

    private CartRepository cartRepository;

    InventoryService(ItemRepository repository, CartRepository cartRepository) {
        this.itemRepository = repository;
        this.cartRepository = cartRepository;
    }

    public Iterable<Cart> getAllCarts() {
        return this.cartRepository.findAll();
    }

    public Cart newCart() {
        return this.cartRepository.save(new Cart("cart"));
    }

    public Optional<Cart> getCart(String cartId) {
        return this.cartRepository.findById(cartId);
    }

    public Iterable<Item> getInventory() {
        return this.itemRepository.findAll();
    }

    public Item saveItem(Item newItem) {
        return this.itemRepository.save(newItem);
    }

    public void deleteItem(Integer id) {
        this.itemRepository.deleteById(id);
    }

    public Cart addItemToCart(String cartId, Integer itemId) {
        Cart cart = this.cartRepository.findById(cartId) //
                .orElseGet(() -> new Cart("My Cart")); //

        cart.getCartItems().stream() //
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId)) //
                .findAny() //
                .map(cartItem -> {
                    cartItem.increment();
                    return cart;
                }) //
                .orElseGet(() -> {
                    this.itemRepository.findById(itemId) //
                            .map(item -> new CartItem(item)) //
                            .map(cartItem -> {
                                cart.getCartItems().add(cartItem);
                                return cart;
                            }) //
                            .orElseGet(() -> cart);
                    return cart;
                });

        return this.cartRepository.save(cart);
    }

    public Cart removeOneFromCart(String cartId, Integer itemId) {
        Cart cart = this.cartRepository.findById(cartId) .orElseGet(() -> new Cart("My Cart"));

        cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findAny()
                .ifPresent(cartItem -> {
                    cartItem.decrement();
                });

        List<CartItem> updatedCartItems = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getQuantity() > 0)
                .collect(Collectors.toList());

        cart.setCartItems(updatedCartItems);

        return this.cartRepository.save(cart);
    }
}
