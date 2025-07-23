package org.locadora.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class UtilizacaoDoConsolePeloCliente implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Console console;

    public UtilizacaoDoConsolePeloCliente() {}

    public UtilizacaoDoConsolePeloCliente(LocalDateTime inicio, LocalDateTime fim, Cliente cliente, Console console) {
        this.inicio = inicio;
        this.fim = fim;
        this.cliente = cliente;
        this.console = console;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Console getConsole() { return console; }
    public void setConsole(Console console) { this.console = console; }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UtilizacaoDoConsolePeloCliente that = (UtilizacaoDoConsolePeloCliente) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
