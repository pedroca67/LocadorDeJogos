package org.locadora.modelo;

import jakarta.persistence.*;
import java.util.ArrayList; // Importar
import java.util.List;
import java.util.Objects;

@Entity
public class Jogo implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL)
    private List<JogoPlataforma> plataformas = new ArrayList<>(); // Adicionar inicialização aqui

    public Jogo() {}

    public Jogo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() { return id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public List<JogoPlataforma> getPlataformas() { return plataformas; }

    public void adicionarPlataforma(JogoPlataforma jogoPlataforma) {
        this.plataformas.add(jogoPlataforma);
        jogoPlataforma.setJogo(this);
    }

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