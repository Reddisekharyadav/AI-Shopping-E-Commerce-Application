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
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final String CART_ITEMS = "cartItems"; // Define constant for repeated string literal

    private final CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Display the cart page with a list of cart items
    @GetMapping
    public String viewCart(Model model) {
        logger.info("Accessing /cart endpoint to view cart items.");
        model.addAttribute(CART_ITEMS, cartService.getAllItems());
        logger.info("Cart items added to model: {}", cartService.getAllItems());
        return "Cart"; // Thymeleaf template at src/main/resources/templates/Cart.html
    }

    // Handle adding a new item
    @PostMapping("/addItem")
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

    // Fetch and display all cart items
    @GetMapping("/items")
    public String getCartItems(Model model) {
        List<CartItem> cartItems = cartService.getAllItems();
        model.addAttribute(CART_ITEMS, cartItems);
        return "Cart"; // Thymeleaf template at src/main/resources/templates/Cart.html
    }

    // Add to cart handler
    @PostMapping("/add")
    public String addToCart(@RequestParam String productName, @RequestParam BigDecimal price,
            RedirectAttributes redirectAttributes) {
        logger.info("POST /cart/add endpoint hit with productName: {} and price: {}", productName, price);
        CartItem item = new CartItem(productName, price);
        cartService.addItem(item);
        logger.info("Item added to cart: {}", item);

        // Add success message
        redirectAttributes.addFlashAttribute("successMessage", "Item added to cart successfully!");

        // Redirect to the cart page
        return "redirect:/cart/items";
    }
}