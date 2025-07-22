package org.locadora.modelo;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class ItemLocacao implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer dias;

    @ManyToOne
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    @ManyToOne
    @JoinColumn(name = "jogoplataforma_id")
    private JogoPlataforma jogoPlataforma; // Adicionado para associar o jogo e a plataforma

    // --- Getters e Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getDias() { return dias; }
    public void setDias(Integer dias) { this.dias = dias; }
    public Locacao getLocacao() { return locacao; }
    public void setLocacao(Locacao locacao) { this.locacao = locacao; }
    public JogoPlataforma getJogoPlataforma() { return jogoPlataforma; }
    public void setJogoPlataforma(JogoPlataforma jogoPlataforma) { this.jogoPlataforma = jogoPlataforma; }


    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemLocacao that = (ItemLocacao) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}