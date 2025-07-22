package org.locadora.testes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.locadora.modelo.Cliente;
import org.locadora.repositorio.ClienteRepository;

import java.util.Scanner;

public class ClienteMain {

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager manager = factory.createEntityManager();
        EntityTransaction transacao = manager.getTransaction();

        Scanner scanner = new Scanner(System.in);

        try {
            // --- Interação com o Usuário ---
            System.out.println("--- Cadastro de Novo Cliente ---");
            System.out.print("Digite o nome: ");
            String nome = scanner.nextLine();

            System.out.print("Digite o e-mail: ");
            String email = scanner.nextLine();

            System.out.print("Digite o telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            // --- Lógica de Negócio ---
            transacao.begin();

            ClienteRepository clienteRepository = new ClienteRepository(manager);
            Cliente novoCliente = new Cliente(nome, email, telefone, senha);

            clienteRepository.salvaOuAtualiza(novoCliente);

            transacao.commit();

            System.out.println("\n>>> Cliente '" + novoCliente.getNome() + "' salvo com sucesso! ID: " + novoCliente.getId());

        } catch (Exception e) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            e.printStackTrace();
        } finally {
            manager.close();
            factory.close();
            scanner.close();
        }
    }
}