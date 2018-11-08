package com.mcb.javajuniortask.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ClientDTO {
    private UUID id;
    private String name;
    private BigDecimal totalDebt;

    public ClientDTO(){
    }

    public ClientDTO(UUID id, String name, BigDecimal totalDebt) {
        this.id = id;
        this.name = name;
        this.totalDebt = totalDebt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
    }
}
