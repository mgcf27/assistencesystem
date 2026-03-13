package com.miguel.assistencesystem.domain.model;

import com.miguel.assistencesystem.domain.enums.ProductIdentificationStatus;
import jakarta.persistence.*;


@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductIdentificationStatus identificationStatus;

    @Column(nullable = false, length = 100)
    private String model; // description at first, precise later

    @Column(length = 100)
    private String commercialModel;

    @Column(length = 50)
    private String manufacturerCode;

    @Column(unique = true, length = 50)
    private String serialNumber;

    @Column(length = 20)
    private String voltage;
    
    @Version
    private Long version;

    /* JPA only */
    protected Product() {}

    /* ========== FACTORIES ========== */

    public static Product registerUnidentified(Client owner, String description) {
        Product p = new Product();
        p.client = owner;
        owner.attachProduct(p);
        p.model = description;
        p.identificationStatus = ProductIdentificationStatus.UNIDENTIFIED;
        return p;
    }

    public static Product registerIdentified(
            Client owner,
            String model,
            String commercialModel,
            String manufacturerCode,
            String serialNumber,
            String voltage) {

        Product p = new Product();
        p.client = owner;
        owner.attachProduct(p);
        p.model = model;
        p.commercialModel = commercialModel;
        p.manufacturerCode = manufacturerCode;
        p.serialNumber = serialNumber;
        p.voltage = voltage;
        p.identificationStatus = ProductIdentificationStatus.IDENTIFIED;
        return p;
    }

    /* ========== IDENTIFICATION LIFECYCLE ========== */

    public void identify(
            String model,
            String commercialModel,
            String manufacturerCode,
            String serialNumber,
            String voltage) {

        if (identificationStatus != ProductIdentificationStatus.UNIDENTIFIED) {
            throw new IllegalStateException("Product already identified");
        }

        this.model = model;
        this.commercialModel = commercialModel;
        this.manufacturerCode = manufacturerCode;
        this.serialNumber = serialNumber;
        this.voltage = voltage;
        this.identificationStatus = ProductIdentificationStatus.IDENTIFIED;
    }

    public boolean isIdentified() {
        return identificationStatus == ProductIdentificationStatus.IDENTIFIED;
    }

    /* ========== READ-ONLY ACCESS ========== */

    public Long getId() { return prodId; }
    public String getModel() { return model; }
    public String getCommercialModel() { return commercialModel; }
    public String getManufacturerCode() { return manufacturerCode; }
    public String getSerialNumber() { return serialNumber; }
    public String getVoltage() { return voltage; }
    public Client getClient() {return client; }
}