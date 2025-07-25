package org.locadora.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private List<Acessorio> acessorios = new ArrayList<>();

    @OneToMany(mappedBy = "console", cascade = CascadeType.ALL)
    private List<UtilizacaoDoConsolePeloCliente> utilizacoes = new ArrayList<>();

    public Console() {}

    public Console(String nome, BigDecimal precoPorHora) {
        this.nome = nome;
        this.precoPorHora = precoPorHora;
    }

    public Integer getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public BigDecimal getPrecoPorHora() { return precoPorHora; }
    public void setPrecoPorHora(BigDecimal precoPorHora) { this.precoPorHora = precoPorHora; }
    public List<Acessorio> getAcessorios() { return acessorios; }
    public List<UtilizacaoDoConsolePeloCliente> getUtilizacoes() { return utilizacoes; }

    public void adicionarAcessorio(Acessorio acessorio) {
        this.acessorios.add(acessorio);
        acessorio.setConsole(this);
    }

    public void adicionarUtilizacao(UtilizacaoDoConsolePeloCliente utilizacao) {
        this.utilizacoes.add(utilizacao);
        utilizacao.setConsole(this);
    }

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