package com.miguel.assistencesystem.application.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ClientUpdateDTO {
	@NotNull
	@NotBlank
	@Size(max=255)
	private String name;
	@NotNull
	@NotBlank
	@Size(max=20)
	private String phone;
	@NotNull
	@NotBlank
	@Size(max=200)
	private String address;
	@NotNull
	@NotBlank
	@Email(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	@Size(max=100)
	private String email;     
    
    // Getters
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }
    
    // Setters
    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setEmail(String email) { this.email = email; }
    
    
    public boolean hasUpdates() {
        return name != null || phone != null || 
               address != null || email != null;
    }
}

