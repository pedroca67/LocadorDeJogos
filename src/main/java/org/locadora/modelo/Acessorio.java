package org.locadora.modelo;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Acessorio implements EntidadeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Console console;

    public Acessorio() {}

    public Acessorio(String nome, Console console) {
        this.nome = nome;
        this.console = console;
    }

    // --- Getters e Setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Console getConsole() { return console; }
    public void setConsole(Console console) { this.console = console; }

    // --- equals() e hashCode() ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Acessorio acessorio = (Acessorio) o;
        return Objects.equals(id, acessorio.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}