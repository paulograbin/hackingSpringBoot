package com.greglturnquist.hackingspringboot.classic.repositories;

import com.greglturnquist.hackingspringboot.classic.models.Cart;
import org.springframework.data.repository.CrudRepository;


public interface CartRepository extends CrudRepository<Cart, String> {

}
