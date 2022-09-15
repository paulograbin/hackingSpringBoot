/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.greglturnquist.hackingspringboot.classic;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Greg Turnquist
 */
@RestController
public class ApiItemController {

    private final ItemRepository repository;

    public ApiItemController(ItemRepository repository) {
        this.repository = repository;
    }


    @GetMapping("/api/items")
    Iterable<Item> findAll() {
        return this.repository.findAll();
    }

    @GetMapping("/api/items/{id}")
    Optional<Item> findOne(@PathVariable Integer id) {
        return this.repository.findById(id);
    }

    @PostMapping("/api/items")
    ResponseEntity<?> addNewItem(@RequestBody Item item) {
        Item savedItem = this.repository.save(item);

        return ResponseEntity.created(URI.create("/api/items/" + //
                savedItem.getId())).body(savedItem);
    }

    @PutMapping("/api/items/{id}")
    public ResponseEntity<?> updateItem( //
                                         @RequestBody Item item, @PathVariable Integer id) {

        Item newItem = new Item(id, item.getName(), item.getDescription(), item.getPrice());

        this.repository.save(newItem);

        return ResponseEntity.created(URI.create("/api/items/" + id)).build();
    }
}