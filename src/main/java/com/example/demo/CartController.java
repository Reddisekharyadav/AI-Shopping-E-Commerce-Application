// package com.example.demo;

// import org.springframework.web.bind.annotation.*;
// import java.math.BigDecimal;
// import java.util.List;

// @RestController
// @RequestMapping("/api/cart")
// public class CartController {

//     private final CartService cartService;

//     public CartController(CartService cartService) {
//         this.cartService = cartService;
//     }

//     @GetMapping
//     public List<CartItem> getCartItems() {
//         return cartService.getAllItems();
//     }

//     @PostMapping("/add")
//     public CartItem addItem(@RequestParam String productName,
//             @RequestParam BigDecimal price) {
//         CartItem item = new CartItem(productName, price);
//         return cartService.addItem(item);
//     }

//     @PostMapping("/delete")
//     public void deleteItem(@RequestParam Long id) {
//         cartService.deleteItem(id);
//     }
// }
package com.example.demo;

import java.math.BigDecimal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Display the cart page with a list of cart items
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getAllItems());
        return "cart"; // Thymeleaf template at src/main/resources/templates/cart.html
    }

    // Handle adding a new item
    @PostMapping("/add")
    public String addItem(@RequestParam String productName,
            @RequestParam BigDecimal price) {
        CartItem item = new CartItem(productName, price);
        cartService.addItem(item);
        return "redirect:/cart"; // Redirects back to the cart page
    }

    // Handle deleting an item
    @PostMapping("/delete")
    public String deleteItem(@RequestParam Long id) {
        cartService.deleteItem(id);
        return "redirect:/cart"; // Redirects back to the cart page
    }
}