package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JogoMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("=== Cadastro de Jogo ===");

            System.out.print("Título do jogo: ");
            String titulo = scanner.nextLine();

            PlataformaRepository plataformaRepo = new PlataformaRepository(em);
            JogoRepository jogoRepo = new JogoRepository(em);

            List<JogoPlataforma> listaPlataformas = new ArrayList<>();
            boolean maisPlataformas = true;

            while (maisPlataformas) {
                System.out.print("Nome da plataforma (ex: Xbox, PS4, PC): ");
                String nomePlataforma = scanner.nextLine();

                Plataforma plataforma = plataformaRepo.buscaPorNomeExato(nomePlataforma);

                if (plataforma == null) {
                    tx.begin();
                    plataforma = new Plataforma(nomePlataforma);
                    plataformaRepo.salvaOuAtualiza(plataforma);
                    tx.commit();
                    System.out.println("Plataforma '" + nomePlataforma + "' criada.");
                } else {
                    System.out.println("Plataforma '" + nomePlataforma + "' já existe.");
                }

                System.out.print("Preço diário para a plataforma " + nomePlataforma + ": ");
                BigDecimal precoDiario = new BigDecimal(scanner.nextLine());

                JogoPlataforma jp = new JogoPlataforma();
                jp.setPlataforma(plataforma);
                jp.setPrecoDiario(precoDiario);
                listaPlataformas.add(jp);

                System.out.print("Deseja adicionar outra plataforma para esse jogo? (S/N): ");
                String resposta = scanner.nextLine().trim().toUpperCase();
                maisPlataformas = resposta.equals("S");
            }

            Jogo jogo = new Jogo(titulo);
            for (JogoPlataforma jp : listaPlataformas) {
                jp.setJogo(jogo);
            }
            jogo.setPlataformas(listaPlataformas);

            tx.begin();
            jogoRepo.salvaOuAtualiza(jogo);
            tx.commit();

            System.out.println("Jogo '" + titulo + "' cadastrado com sucesso!");

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }
}
