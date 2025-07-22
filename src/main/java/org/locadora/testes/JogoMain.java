package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.Jogo;
import org.locadora.service.JogoService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JogoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager em = emf.createEntityManager();
        Scanner sc = new Scanner(System.in);
        JogoService jogoService = new JogoService(em);

        System.out.println("--- Cadastro de Novo Jogo ---");
        System.out.print("Título do Jogo: ");
        String titulo = sc.nextLine();
        System.out.print("Plataformas (separadas por vírgula, ex: PC,PS5,XBOX): ");
        String plataformasInput = sc.nextLine();
        List<String> plataformas = Arrays.asList(plataformasInput.split(","));
        System.out.print("Preço diário de locação: ");
        BigDecimal preco = sc.nextBigDecimal();

        try {
            Jogo jogoSalvo = jogoService.salvar(titulo, plataformas, preco);
            System.out.println("\nJogo cadastrado com sucesso! ID: " + jogoSalvo.getId());
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar jogo: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
            sc.close();
        }
    }
}