package com.greglturnquist.hackingspringboot.classic.repositories;

import com.greglturnquist.hackingspringboot.classic.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


public interface ItemByExampleRepository extends CrudRepository<Item, Long>, QueryByExampleExecutor<Item> {

}
