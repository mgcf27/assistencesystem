package com.miguel.assistencesystem.application.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
	@NotNull
	@NotBlank
	@Email(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	@Size(max=100)
    private String email;
	@NotNull
	@NotBlank
    private String password;

    public LoginRequestDTO() {}

    public LoginRequestDTO(
            String email,
            String password
    ) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

}
