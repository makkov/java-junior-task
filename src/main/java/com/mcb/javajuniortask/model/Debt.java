package com.mcb.javajuniortask.model;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Debt {
    @Id
    private UUID id;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Debt(){
    }

    public Debt(UUID id, BigDecimal value, Client client) {
        this.id = id;
        this.value = value;
        this.client = client;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
