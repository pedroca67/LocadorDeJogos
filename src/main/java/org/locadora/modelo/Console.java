package org.locadora.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Console implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private BigDecimal precoPorHora;

    @OneToMany(mappedBy = "console", cascade = CascadeType.ALL)
    private List<Acessorio> acessorios;

    @OneToMany(mappedBy = "console", cascade = CascadeType.ALL)
    private List<UtilizacaoDoConsolePeloCliente> utilizacoes;

    public Console() {}

    public Console(String nome, BigDecimal precoPorHora) {
        this.nome = nome;
        this.precoPorHora = precoPorHora;
    }

    // --- Getters e Setters ---
    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoPorHora() { return precoPorHora; }
    public void setPrecoPorHora(BigDecimal precoPorHora) { this.precoPorHora = precoPorHora; }
    public List<Acessorio> getAcessorios() { return acessorios; }
    public void setAcessorios(List<Acessorio> acessorios) { this.acessorios = acessorios; }
    public List<UtilizacaoDoConsolePeloCliente> getUtilizacoes() { return utilizacoes; }
    public void setUtilizacoes(List<UtilizacaoDoConsolePeloCliente> utilizacoes) { this.utilizacoes = utilizacoes; }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Console console = (Console) o;
        return Objects.equals(id, console.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}