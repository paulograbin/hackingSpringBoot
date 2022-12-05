package com.greglturnquist.hackingspringboot.classic.repositories;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface CartRepository extends CrudRepository<Cart, String> {

}
// end::code[]