package com.greglturnquist.hackingspringboot.classic.repositories;

import java.util.List;

import com.greglturnquist.hackingspringboot.classic.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {

    List<Item> findByNameContaining(String partialName);

    // search by name
    List<Item> findByNameContainingIgnoreCase(String partialName);

    // search by description
    List<Item> findByDescriptionContainingIgnoreCase(String partialName);

    // search by name AND description
    List<Item> findByNameContainingAndDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

    // search by name OR description
    List<Item> findByNameContainingOrDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);
}