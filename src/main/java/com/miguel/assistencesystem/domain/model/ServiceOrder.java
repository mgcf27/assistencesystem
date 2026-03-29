package com.miguel.assistencesystem.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.miguel.assistencesystem.domain.enums.ServiceOrderStatus;
import com.miguel.assistencesystem.domain.exceptions.serviceorder.InvalidServiceOrderStatusException;
import com.miguel.assistencesystem.domain.valueobjects.ServiceOrderProtocolGenerator;



@Entity
@Table
public class ServiceOrder {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceOrderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prod_id", nullable = false)
    private Product product;
    
    @Column(
    		name = "protocol_number",
    	    nullable = false,
    	    unique = true,
    	    updatable = false,
    	    length = 30)
    private String protocolNumber;
	
	@Column(name = "problem_description")
	private String problemDescription;

	@Column(name = "opened_at", nullable = false)
	private LocalDateTime openedAt;

	@Column(name = "closed_at", nullable = true)
	private LocalDateTime closedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ServiceOrderStatus status;
	
	@Version
	private Long version;

	//===============JPA only( java reflection)=============== 
    protected ServiceOrder() {}

    /* Factory method */
    public static ServiceOrder open(Client client, Product product, String problemDescription) {
        ServiceOrder so = new ServiceOrder();
        so.protocolNumber = ServiceOrderProtocolGenerator.generate();
        so.client = client;
        so.product = product;
        so.problemDescription = problemDescription;
        so.openedAt = LocalDateTime.now();
        so.status = ServiceOrderStatus.OPEN;
        return so;
    }

    // ================= STATE MACHINE =================

    public void startProgress() {
        transitionTo(ServiceOrderStatus.IN_PROGRESS);
    }
    
    public void waitForParts() {
        transitionTo(ServiceOrderStatus.WAITING_PARTS);
    }

    public void finish() {
        transitionTo(ServiceOrderStatus.FINISHED);
    }

    public void close() {
        transitionTo(ServiceOrderStatus.CLOSED);
    }

    public void cancel() {
        transitionTo(ServiceOrderStatus.CANCELED);
    }

    private void transitionTo(ServiceOrderStatus next) {
        validateTransition(this.status, next);
        applySideEffects(this.status, next);
        this.status = next;
    }

    private void validateTransition(ServiceOrderStatus current, ServiceOrderStatus next) {
        switch (current) {
            case OPEN:
                allow(next, ServiceOrderStatus.IN_PROGRESS, ServiceOrderStatus.CANCELED);
                break;
            case IN_PROGRESS:
                allow(next, ServiceOrderStatus.FINISHED, ServiceOrderStatus.WAITING_PARTS);
                break;
            case WAITING_PARTS:
                allow(next, ServiceOrderStatus.IN_PROGRESS, ServiceOrderStatus.FINISHED);
                break;
            case FINISHED:
                allow(next, ServiceOrderStatus.CLOSED);
                break;
            case CLOSED:
            case CANCELED:
                throw new InvalidServiceOrderStatusException(current);
        }
    }

    private void allow(ServiceOrderStatus next, ServiceOrderStatus... allowed) {
        for (ServiceOrderStatus s : allowed) {
            if (s == next) return;
        }
        throw new InvalidServiceOrderStatusException(status, next);
    }

    private void applySideEffects(ServiceOrderStatus from, ServiceOrderStatus to) {
        if (to == ServiceOrderStatus.CLOSED || to == ServiceOrderStatus.CANCELED) {
            this.closedAt = LocalDateTime.now();
        }
    }

    // ================= INVARIANTS =================

    @PrePersist
    @PreUpdate
    private void validateConsistency() {
        if (!product.getClient().equals(client)) {
            throw new IllegalStateException("Product must belong to same client as service order");
        }
    }

    // ================= READ-ONLY ACCESS =================

    public Long getId() { return serviceOrderId; }
    public Client getClient() { return client; }
    public Product getProduct() { return product; }
    public String getProtocolNumber() { return protocolNumber; }
    public String getProblemDescription() { return problemDescription; }
    public LocalDateTime getOpenedAt() { return openedAt; }
    public LocalDateTime getClosedAt() { return closedAt; }
    public ServiceOrderStatus getStatus() { return status; }
    public Long getVersion() { return version; }
}