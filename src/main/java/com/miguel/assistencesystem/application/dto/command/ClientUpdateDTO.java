package com.miguel.assistencesystem.application.dto.command;

public class ClientUpdateDTO {
    private String name;        
    private String phone;       
    private String address;     
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

