package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Jogo;

import java.util.List;

public class JogoRepository {

    private final EntityManager gere;

    public JogoRepository(EntityManager gere) {
        this.gere = gere;
    }

    public Jogo salva(Jogo jogo) {
        if (jogo.getId() == null) {
            gere.persist(jogo);
            return jogo;
        } else {
            return gere.merge(jogo);
        }
    }

    public List<Jogo> buscaPorTitulo(String titulo) {
        return gere.createQuery("select j from Jogo j where upper(j.titulo) like :titulo", Jogo.class)
                .setParameter("titulo", "%" + titulo.toUpperCase() + "%")
                .getResultList();
    }
}
