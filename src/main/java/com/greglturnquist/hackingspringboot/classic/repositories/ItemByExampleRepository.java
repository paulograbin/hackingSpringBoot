package com.greglturnquist.hackingspringboot.classic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * @author Greg Turnquist
 */
// tag::code[]
public interface ItemByExampleRepository extends CrudRepository<Item, Long>, QueryByExampleExecutor<Item> {

}
// end::code[]