package org.locadora.modelo;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class JogoPlataforma implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;


    private BigDecimal precoDiario;

    public JogoPlataforma() {}

    public JogoPlataforma(Jogo jogo, Plataforma plataforma, BigDecimal precoDiario) {
        this.jogo = jogo;
        this.plataforma = plataforma;
        this.precoDiario = precoDiario;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Jogo getJogo() { return jogo; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }
    public Plataforma getPlataforma() { return plataforma; }
    public void setPlataforma(Plataforma plataforma) { this.plataforma = plataforma; }
    public BigDecimal getPrecoDiario() { return precoDiario; }
    public void setPrecoDiario(BigDecimal precoDiario) { this.precoDiario = precoDiario; }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JogoPlataforma that = (JogoPlataforma) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
