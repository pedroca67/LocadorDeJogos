package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.JogoPlataforma;

public class JogoPlataformaRepository {

    private final DAOGenerico<JogoPlataforma> dao;
    private final EntityManager em;

    public JogoPlataformaRepository(EntityManager em) {
        this.em = em;
        this.dao = new DAOGenerico<>(em);
    }

    public JogoPlataforma buscaPorId(Integer id) {
        return dao.buscaPorId(JogoPlataforma.class, id);
    }

}