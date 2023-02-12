package com.greglturnquist.hackingspringboot.classic;

import com.greglturnquist.hackingspringboot.classic.models.Item;
import com.greglturnquist.hackingspringboot.classic.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class TemplateDatabaseLoader {

    @Bean
    CommandLineRunner initialize(ItemRepository repository) {
        return args -> {
            repository.save(new Item("Alf alarm clock", "kids clock", 19.99));
            repository.save(new Item("Smurf TV tray", "kids TV tray", 24.99));
            repository.save(new Item("aaaaaa", "aaaaaaaa", 14.99));
            repository.save(new Item("bbbbbbb", "bbbbb", 34.99));
        };
    }
}