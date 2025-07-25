package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.JogoPlataforma;

public class JogoPlataformaRepository {

    private final DAOGenerico<JogoPlataforma> dao;
    private final EntityManager e1;

    public JogoPlataformaRepository(EntityManager e1) {
        this.e1 = e1;
        this.dao = new DAOGenerico<>(e1);
    }

    public JogoPlataforma buscaPorId(Integer id) {
        return dao.buscaPorId(JogoPlataforma.class, id);
    }

}