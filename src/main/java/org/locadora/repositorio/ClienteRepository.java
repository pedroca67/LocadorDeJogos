package org.locadora.repositorio;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Cliente;
import java.util.List;

public class ClienteRepository {

    private final EntityManager manager;
    private final DAOGenerico<Cliente> daoGenerico;

    public ClienteRepository(EntityManager manager) {
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    // Método para buscar um cliente pelo ID
    public Cliente buscaPorId(Integer id) {
        return daoGenerico.buscaPorId(Cliente.class, id);
    }

    // Método para buscar clientes pelo nome (exemplo útil)
    public List<Cliente> buscaPorNome(String nome) {
        return this.manager.createQuery("from Cliente where upper(nome) like :nome", Cliente.class)
                .setParameter("nome", "%" + nome.toUpperCase() + "%")
                .getResultList();
    }

    // Método que implementa a funcionalidade de "salvar ou atualizar" o cliente
    public Cliente salvaOuAtualiza(Cliente cliente) {
        return daoGenerico.salvaOuAtualiza(cliente);
    }

}