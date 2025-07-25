package org.locadora.service;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Cliente;
import org.locadora.repositorio.ClienteRepository;

public class ClienteService {

    private final EntityManager e1;
    private final ClienteRepository clienteR;

    public ClienteService(EntityManager e1) {
        this.e1 = e1;
        this.clienteR = new ClienteRepository(e1);
    }

    public Cliente salvar(String nome, String email, String telefone, String senha) {

        e1.getTransaction().begin();

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setEmail(email);
        novoCliente.setTelefone(telefone);
        novoCliente.setSenha(senha);

        clienteR.salvaOuAtualiza(novoCliente);

        e1.getTransaction().commit();
        return novoCliente;
    }
}