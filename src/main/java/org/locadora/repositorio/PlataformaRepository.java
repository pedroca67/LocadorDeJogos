package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.locadora.modelo.Plataforma;

import java.util.List;

public class PlataformaRepository {

    private final EntityManager manager;

    public PlataformaRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Plataforma salvaOuAtualiza(Plataforma plataforma) {
        if (plataforma.getId() == null) {
            manager.persist(plataforma);
            return plataforma;
        } else {
            return manager.merge(plataforma);
        }
    }

    public Plataforma buscaPorNomeExato(String nome) {
        try {
            return manager.createQuery("SELECT p FROM Plataforma p WHERE p.nome = :nome", Plataforma.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Plataforma> buscaTodos() {
        return manager.createQuery("FROM Plataforma", Plataforma.class).getResultList();
    }
}
