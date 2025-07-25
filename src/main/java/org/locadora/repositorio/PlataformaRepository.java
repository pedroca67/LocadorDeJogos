package org.locadora.repositorio;

import jakarta.persistence.*;
import org.locadora.modelo.*;

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
