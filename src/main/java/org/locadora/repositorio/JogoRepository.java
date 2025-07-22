package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Jogo;

import java.util.List;

public class JogoRepository {

    private final EntityManager manager;

    public JogoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Jogo salvaOuAtualiza(Jogo jogo) {
        if (jogo.getId() == null) {
            manager.persist(jogo);
            return jogo;
        } else {
            return manager.merge(jogo);
        }
    }

    public Jogo buscaPorId(Integer id) {
        return manager.find(Jogo.class, id);
    }

    public List<Jogo> buscaPorTitulo(String titulo) {
        return manager.createQuery("SELECT j FROM Jogo j WHERE UPPER(j.titulo) LIKE :titulo", Jogo.class)
                .setParameter("titulo", "%" + titulo.toUpperCase() + "%")
                .getResultList();
    }
}
