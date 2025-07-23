package org.locadora.service;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Cliente;
import org.locadora.repositorio.ClienteRepository;

public class ClienteService {

    private final EntityManager em;
    private final ClienteRepository clienteRepository;

    public ClienteService(EntityManager em) {
        this.em = em;
        this.clienteRepository = new ClienteRepository(em);
    }

    public Cliente salvar(String nome, String email, String telefone, String senha) {

        em.getTransaction().begin();

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setEmail(email);
        novoCliente.setTelefone(telefone);
        novoCliente.setSenha(senha);

        clienteRepository.salvaOuAtualiza(novoCliente);

        em.getTransaction().commit();
        return novoCliente;
    }
}