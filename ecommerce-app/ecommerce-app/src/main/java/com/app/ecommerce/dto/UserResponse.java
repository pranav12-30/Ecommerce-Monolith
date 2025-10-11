package com.app.ecommerce.dto;

import com.app.ecommerce.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO address;
}
