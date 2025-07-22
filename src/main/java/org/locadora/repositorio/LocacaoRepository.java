package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Locacao;

public class LocacaoRepository {

    private final EntityManager manager;

    public LocacaoRepository(EntityManager manager) {
        this.manager = manager;
    }

    public Locacao salvaOuAtualiza(Locacao locacao) {
        if (locacao.getId() == null) {
            manager.persist(locacao);
            return locacao;
        } else {
            return manager.merge(locacao);
        }
    }
}
