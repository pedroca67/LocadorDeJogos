package org.locadora.service;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.Jogo;
import org.locadora.modelo.JogoPlataforma;
import org.locadora.modelo.Plataforma;
import org.locadora.repositorio.JogoRepository;
import org.locadora.repositorio.PlataformaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JogoService {

    private final EntityManager em;
    private final JogoRepository jogoRepository;
    private final PlataformaRepository plataformaRepository;

    public JogoService(EntityManager em) {
        this.em = em;
        this.jogoRepository = new JogoRepository(em);
        this.plataformaRepository = new PlataformaRepository(em);
    }

    public Jogo salvar(String titulo, List<String> nomesPlataformas, BigDecimal precoDiario) {
        em.getTransaction().begin();

        try {
            Jogo novoJogo = new Jogo(titulo);
            List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

            for (String nomePlataforma : nomesPlataformas) {
                Plataforma plataforma = plataformaRepository.buscaPorNomeExato(nomePlataforma);
                if (plataforma == null) {
                    plataforma = new Plataforma(nomePlataforma);
                }

                JogoPlataforma jogoPlataforma = new JogoPlataforma();
                jogoPlataforma.setJogo(novoJogo); // Associa a JogoPlataforma ao novo Jogo
                jogoPlataforma.setPlataforma(plataforma);
                jogoPlataforma.setPrecoDiario(precoDiario);
                jogoPlataformas.add(jogoPlataforma);
            }

            // Define a lista completa de plataformas no jogo
            novoJogo.setPlataformas(jogoPlataformas);

            // Persiste o Jogo, e o Cascade.ALL cuidará de persistir os JogoPlataforma
            jogoRepository.salvaOuAtualiza(novoJogo);

            em.getTransaction().commit();
            return novoJogo;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Lança a exceção para a camada que chamou o serviço saber do erro
            throw new RuntimeException("Falha ao salvar o jogo: " + e.getMessage(), e);
        }
    }
}