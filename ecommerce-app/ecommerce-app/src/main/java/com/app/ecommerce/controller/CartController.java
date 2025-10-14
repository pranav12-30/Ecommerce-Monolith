package com.app.ecommerce.controller;

import com.app.ecommerce.dto.CartItemRequest;
import com.app.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // allows users to add item to cart.
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartItemRequest cartItemRequest,
                                            @RequestHeader("user-id") String userId) {
        boolean addedToCart = cartService.addToCart(userId, cartItemRequest);
        if (!addedToCart) {
            return ResponseEntity.badRequest().body("Product not found or Product Out Of Stock or User not found");
        }
        return new ResponseEntity<>("Item added to cart successfully by the user.", HttpStatus.CREATED);
    }
}
