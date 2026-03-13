package com.miguel.assistencesystem.domain.model;

import java.util.*;
import jakarta.persistence.*;




@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false, updatable = false, unique = true, length = 14)
    private String cpf;   // Legal identity – immutable

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false,unique = true, length = 20)
    private String phone;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(length = 100)
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    @Version
    private Long version;

    /* JPA only */
    protected Client() {}

    /* Factory */
    public static Client register(String name, String cpf, String phone, String address, String email) {
        Client c = new Client();
        c.name = name;
        c.cpf = cpf;
        c.phone = phone;
        c.address = address;
        c.email = email;
        return c;
    }

    // ================= IDENTITY =================

    public Long getId() { return clientId; }
    public String getCpf() { return cpf; }

    // ================= PROFILE =================

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getEmail() { return email; }

    public void updateProfile(String name, String phone, String address, String email) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    // ================= READ ONLY (VIEW PURPOSES) =================

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }
    
    public List<ServiceOrder
    > getServiceOrders() {
    	return Collections.unmodifiableList(serviceOrders);
    }
    // ================= PRODUCTS =================
    void attachProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    void detachProduct(Product product) {
        products.remove(product);
    }

    // ================= INVARIANTS =================

    @PreUpdate
    private void enforceIdentity() {
        // CPF is immutable after creation
        if (cpf == null) {
            throw new IllegalStateException("CPF cannot be null");
        }
    }

}
