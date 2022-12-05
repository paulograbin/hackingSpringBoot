package com.greglturnquist.hackingspringboot.classic.web;

import com.greglturnquist.hackingspringboot.classic.Cart;
import com.greglturnquist.hackingspringboot.classic.InventoryService;
import com.greglturnquist.hackingspringboot.classic.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class HomeController {

    private final InventoryService inventoryService;

    public HomeController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    String home(Model model) {
        Iterable<Item> items = this.inventoryService.getInventory();
        model.addAttribute("items", items);

        Cart cart = this.inventoryService.getCart("My Cart")
                .orElseGet(() -> new Cart("My Cart"));
        model.addAttribute("cart", cart);

        return "home";
    }

    @PostMapping("/add/{id}")
    String addToCart(@PathVariable Integer id) {
        this.inventoryService.addItemToCart("My Cart", id);
        return "redirect:/";
    }

    @DeleteMapping("/remove/{id}")
    String removeFromCart(@PathVariable Integer id) {
        this.inventoryService.removeOneFromCart("My Cart", id);
        return "redirect:/";
    }

    @PostMapping
    String createItem(@RequestBody Item newItem) {
        this.inventoryService.saveItem(newItem);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    String deleteItem(@PathVariable Integer id) {
        this.inventoryService.deleteItem(id);
        return "redirect:/";
    }
}