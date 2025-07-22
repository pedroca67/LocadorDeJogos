package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.Cliente;
import org.locadora.service.ClienteService;
import java.util.Scanner;

public class ClienteMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager em = emf.createEntityManager();
        Scanner sc = new Scanner(System.in);
        ClienteService clienteService = new ClienteService(em);

        System.out.println("--- Cadastro de Novo Cliente ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();
        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            Cliente clienteSalvo = clienteService.salvar(nome, email, telefone, senha);
            System.out.println("\nCliente cadastrado com sucesso! ID: " + clienteSalvo.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
            sc.close();
        }
    }
}