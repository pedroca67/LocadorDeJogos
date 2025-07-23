package org.locadora.modelo;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Jogo implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<JogoPlataforma> plataformas;

    public Jogo() {}

    public Jogo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public List<JogoPlataforma> getPlataformas() { return plataformas; }
    public void setPlataformas(List<JogoPlataforma> plataformas) { this.plataformas = plataformas; }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogo jogo = (Jogo) o;
        return Objects.equals(id, jogo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
