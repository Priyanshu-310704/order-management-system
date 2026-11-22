package com.prysh.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Name Cannot Be Blank")
        @Size(max = 50,message = "Name Cannot exceed 50 charachters")
        String name,
        @NotBlank(message = "Email Cannot Be Blank")
        @Email(message = "Please Provide a Valid Email")
        @Size(max = 150,message = "Email Cannot exceed 150 characacters")
        String email,
        @NotBlank(message = "Phone no. cannot be Empty")
        @Size(max = 12,message = "Please Provide a Valid Phone Number")
        String phone
) {
}
