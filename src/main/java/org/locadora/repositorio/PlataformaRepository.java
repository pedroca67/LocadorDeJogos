package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.locadora.modelo.Plataforma;

public class PlataformaRepository {

    private final EntityManager manager;

    public PlataformaRepository(EntityManager manager) {
        this.manager = manager;
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
}
