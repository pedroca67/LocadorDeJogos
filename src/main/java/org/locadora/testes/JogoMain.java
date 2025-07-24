package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.service.*;
import java.math.*;
import java.util.*;

public class JogoMain {
    public static void main(String[] args) {
        EntityManagerFactory a1 = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager a2 = a1.createEntityManager();
        Scanner s = new Scanner(System.in);
        JogoService jogoService = new JogoService(a2);

        System.out.println("--- Cadastro de Novo Jogo ---");

        System.out.print("Título do Jogo: ");
        String titulo = s.nextLine().trim();
        if (titulo.isEmpty()) {
            System.err.println("Título não pode estar vazio.");
            return;
        }

        System.out.print("Plataformas (separadas por vírgula, ex: PC,PS5,XBOX): ");
        String plataformasInput = s.nextLine().trim();
        List<String> plataformas = Arrays.asList(plataformasInput.split(","));
        if (plataformas.isEmpty()) {
            System.err.println("Erro: Pelo menos uma plataforma deve ser informada.");
            return;
        }

        System.out.print("Preço diário de locação: ");
        BigDecimal preco;
        try {
            preco = s.nextBigDecimal();
            if (preco.compareTo(BigDecimal.ZERO) <= 0) {
                System.err.println("Erro: O preço deve ser positivo.");
                return;
            }
        } catch (InputMismatchException e) {
            System.err.println("Erro: Preço inválido.");
            return;
        }

        try {
            Jogo jogoSalvo = jogoService.salvar(titulo, plataformas, preco);
            System.out.println("\nJogo cadastrado com sucesso! ID: " + jogoSalvo.getId());
        }  catch (Exception e) {
        System.err.println("Erro ao cadastrar jogo:");
        e.printStackTrace();
    }
 finally {
            a2.close();
            a1.close();
            s.close();
        }
    }
}
