package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.service.*;

import java.util.*;

public class ClienteMain {
    public static void main(String[] args) {
        EntityManagerFactory a1 = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager a2 = a1.createEntityManager();
        Scanner s = new Scanner(System.in);
        ClienteService clienteService = new ClienteService(a2);

        System.out.println("--- Cadastro de Novo Cliente ---");

        System.out.print("Nome: ");
        String nome = s.nextLine().trim();
        if (nome.isEmpty()) {
            System.err.println("Erro: Nome não pode estar vazio.");
            return;
        }

        System.out.print("Email: ");
        String email = s.nextLine().trim();
        if (email.isEmpty() || !email.contains("@")) {
            System.err.println("Erro: Email inválido.");
            return;
        }

        System.out.print("Telefone: ");
        String telefone = s.nextLine().trim();
        if (telefone.isEmpty()) {
            System.err.println("Erro: Telefone não pode estar vazio.");
            return;
        }

        System.out.print("Senha: ");
        String senha = s.nextLine().trim();
        if (senha.length() < 4) {
            System.err.println("Erro: Senha deve ter pelo menos 4 caracteres.");
            return;
        }

        try {
            Cliente clienteSalvo = clienteService.salvar(nome, email, telefone, senha);
            System.out.println("\nCliente cadastrado com sucesso! ID: " + clienteSalvo.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        } finally {
            a2.close();
            a1.close();
            s.close();
        }
    }
}
