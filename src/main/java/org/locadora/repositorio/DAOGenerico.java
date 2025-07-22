package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.EntidadeBase;

import java.util.Objects;

class DAOGenerico<T extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    T buscaPorId(Class<T> clazz, Integer id) {
        return manager.find(clazz, id);
    }

    T salvaOuAtualiza(T t) {
        // Se o ID do objeto é nulo, significa que ele é novo e deve ser inserido.
        if(Objects.isNull(t.getId())) {
            this.manager.persist(t);
        } else {
            // Caso contrário, ele já existe e deve ser atualizado.
            t = this.manager.merge(t);
        }
        return t;
    }

    void remove(T t) {
        // Remove o objeto do banco de dados.
        manager.remove(t);
        // O flush garante que a operação de remoção seja sincronizada com o banco imediatamente.
        manager.flush();
    }
}