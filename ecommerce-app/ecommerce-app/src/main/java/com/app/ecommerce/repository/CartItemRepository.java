package com.app.ecommerce.repository;

import com.app.ecommerce.model.CartItem;
import com.app.ecommerce.model.Product;
import com.app.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByUserAndProduct(User user, Product product);

    List<CartItem> findByUser(User user);
}
