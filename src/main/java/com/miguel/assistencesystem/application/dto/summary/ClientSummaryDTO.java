package com.miguel.assistencesystem.application.dto.summary;

import com.miguel.assistencesystem.domain.model.Client;

public class ClientSummaryDTO {
    private final Long id;
    private final String name;
    private final String cpf;
    private final String phone;

    public ClientSummaryDTO(Long id, String name, String cpf, String phone) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCpf() { return cpf; }
    public String getPhone() { return phone; }

    public static ClientSummaryDTO fromEntity(Client client) {
        return new ClientSummaryDTO(
            client.getId(),
            client.getName(),
            client.getCpf(),
            client.getPhone()
        );
    }
}
