package org.locadora.modelo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Locacao implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "locacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemLocacao> itens;

    public Locacao() {}

    public Locacao(LocalDate data, Cliente cliente) {
        this.data = data;
        this.cliente = cliente;
    }

    public Integer getId() { return id; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemLocacao> getItens() { return itens; }
    public void setItens(List<ItemLocacao> itens) { this.itens = itens; }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(item -> {
                    BigDecimal precoDiario = item.getJogoPlataforma().getPrecoDiario();
                    BigDecimal dias = new BigDecimal(item.getDias());
                    BigDecimal quantidade = new BigDecimal(item.getQuantidade());
                    return precoDiario.multiply(dias).multiply(quantidade);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Locacao locacao = (Locacao) o;
        return Objects.equals(id, locacao.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
