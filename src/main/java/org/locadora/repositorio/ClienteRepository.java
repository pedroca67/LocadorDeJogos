package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Cliente;

public class ClienteRepository {

    private final EntityManager gere;
    private final DAOGenerico<Cliente> daoGenerico;

    public ClienteRepository(EntityManager gere) {
        this.gere = gere;
        this.daoGenerico = new DAOGenerico<>(gere);
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Cliente.class, id);
    }

    // Método que implementa a funcionalidade de "salvar ou atualizar" o cliente
    public Cliente salva(Cliente cliente) {
        return daoGenerico.salva(cliente);
    }

}