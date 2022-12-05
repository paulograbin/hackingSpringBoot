package com.greglturnquist.hackingspringboot.classic.models;

import java.util.Objects;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class CartItem {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;
    private int quantity;

    protected CartItem() {
    }

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }



    public void increment() {
        this.quantity++;
    }

    public void decrement() {
        this.quantity--;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CartItem))
            return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(cart, cartItem.cart)
                && Objects.equals(item, cartItem.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cart, item, quantity);
    }

    @Override
    public String toString() {
        String cartId = Optional.ofNullable(this.cart).map(Cart::getId).orElse("NA");
        Integer itemId = Optional.ofNullable(this.item).map(Item::getId).orElse(-1);

        return "CartItem{" + "id=" + id + ", cartId=" + cartId + ", itemId=" + itemId + ", quantity=" + quantity + '}';
    }
}