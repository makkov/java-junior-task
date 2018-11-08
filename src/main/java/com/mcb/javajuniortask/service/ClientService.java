package com.mcb.javajuniortask.service;

import com.mcb.javajuniortask.dto.ClientDTO;
import com.mcb.javajuniortask.model.Client;
import com.mcb.javajuniortask.model.Debt;
import com.mcb.javajuniortask.repository.ClientRepository;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ShellComponent
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @ShellMethod("Shows all clients in db")
    @Transactional
    public Iterable<ClientDTO> showAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false).map(client -> {
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setName(client.getName());
            clientDTO.setTotalDebt(client.getDebts().stream().map(Debt::getValue).reduce(BigDecimal::add).orElse(BigDecimal.ZERO));
            return clientDTO;
        }).collect(Collectors.toList());
    }

    @ShellMethod("Adds client to db")
    @Transactional
    public UUID addClient(@ShellOption String name) {
        Client client = new Client();
        client.setName(name);
        client.setId(UUID.randomUUID());
        client = clientRepository.save(client);
        return client.getId();
    }

    @ShellMethod("Adds debt to client")
    @Transactional
    public UUID addDebtToClient(@ShellOption UUID clientId, @ShellOption BigDecimal value) {
        Client client = clientRepository.findOne(clientId);
        Debt debt = new Debt();
        debt.setValue(value);
        debt.setId(UUID.randomUUID());
        debt.setClient(client);
        client.getDebts().add(debt);
        clientRepository.save(client);
        return debt.getId();
    }

    @ShellMethod("Writes off part of db")
    @Transactional
    public void writeOffPartOfDebt(@ShellOption UUID clientId, @ShellOption BigDecimal amountOfPayment) {
        Client client = clientRepository.findOne(clientId);
        BigDecimal totalDebt = client.getDebts().stream().map(Debt::getValue).reduce(BigDecimal::add).get();
        if (totalDebt.compareTo(amountOfPayment) < 0) {
            System.out.println("Payment of the above debt. After the transaction, the balance will be\n " +
                    totalDebt.subtract(amountOfPayment).abs() +
                    " conventional unit");
        }
        /*Для уменьшения общего долга необходимо из суммы задолженностей вычесть вносимый платеж.
         Определим одну операцию назначения долга, которую мы сможем изменять при каждом внесении платежа.
        В данной реализации выбрана первая операция.
         */
        BigDecimal etDeb = client.getDebts().get(0).getValue();
        client.getDebts().get(0).setValue(etDeb.subtract(amountOfPayment));
    }
}
