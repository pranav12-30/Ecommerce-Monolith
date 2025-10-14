package com.app.ecommerce.service;

import com.app.ecommerce.dto.CartItemRequest;
import com.app.ecommerce.model.CartItem;
import com.app.ecommerce.model.Product;
import com.app.ecommerce.model.User;
import com.app.ecommerce.repository.CartItemRepository;
import com.app.ecommerce.repository.ProductRepository;
import com.app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        Integer requestedQuantity = cartItemRequest.getQuantity();
        Product product = productRepository.findById(cartItemRequest.getProductId())
                .orElse(null);
        if (isNull(product) || product.getStockQuantity() < requestedQuantity) {
            return false;
        }

        User user = userRepository.findById(Long.valueOf(userId))
                .orElse(null);
        if (isNull(user)) {
            return false;
        }

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (!isNull(existingCartItem)) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + requestedQuantity);
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .user(user)
                    .quantity(requestedQuantity)
                    .price(product.getPrice().multiply(BigDecimal.valueOf(requestedQuantity)))
                    .build();
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteItemFromCart(String userId, Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);
        User user = userRepository.findById(Long.valueOf(userId))
                .orElse(null);
        if (isNull(product) || isNull(user)) {
            return false;
        }

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (isNull(cartItem)) return false;
        cartItemRepository.deleteById(cartItem.getId());
        return true;
    }
}
