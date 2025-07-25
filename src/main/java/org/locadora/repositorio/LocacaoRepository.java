package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Locacao;

public class LocacaoRepository {

    private final EntityManager gere;

    public LocacaoRepository(EntityManager gere) {
        this.gere = gere;
    }

    public Locacao salvar(Locacao locacao) {
        if (locacao.getId() == null) {
            gere.persist(locacao);
            return locacao;
        } else {
            return gere.merge(locacao);
        }
    }
}
