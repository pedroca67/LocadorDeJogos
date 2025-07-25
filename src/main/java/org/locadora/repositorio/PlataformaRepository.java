package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.locadora.modelo.Plataforma;

public class PlataformaRepository {

    private final EntityManager gere;

    public PlataformaRepository(EntityManager gere) {
        this.gere = gere;
    }

    public Plataforma buscaPorNome(String nome) {
        try {
            return gere.createQuery("select p from Plataforma p where p.nome = :nome", Plataforma.class).setParameter("nome", nome).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
