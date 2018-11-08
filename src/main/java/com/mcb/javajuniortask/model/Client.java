package com.mcb.javajuniortask.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "client")
    @Cascade(CascadeType.ALL)
    List<Debt> debts = new LinkedList<>();

    public Client() {
    }

    public Client(UUID id, String name, List<Debt> debts) {
        this.id = id;
        this.name = name;
        this.debts = debts;
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

    public List<Debt> getDebts() {
        return debts;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }
}
