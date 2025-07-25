package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.EntidadeBase;

import java.util.Objects;

class DAOGenerico<Entidade extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    Entidade buscaPorId(Class<Entidade> clazz, Integer id) {
        return manager.find(clazz, id);
    }

    Entidade salva(Entidade objt) {
        // Se o ID do objeto é nulo, significa que ele é novo e deve ser inserido.
        if(Objects.isNull(objt.getId())) {
            this.manager.persist(objt);
        } else {
            // Caso contrário, ele já existe e deve ser atualizado, uma implementação que pode ser necessria no futuro
            objt = this.manager.merge(objt);
        }
        return objt;
    }
}