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

    private final EntityManager entidade;
    private final JogoRepository jogoRepository;
    private final PlataformaRepository plataformaRepository;

    public JogoService(EntityManager entidade) {
        this.entidade = entidade;
        this.jogoRepository = new JogoRepository(entidade);
        this.plataformaRepository = new PlataformaRepository(entidade);
    }

    public Jogo salvar(String titulo, List<String> nomesPlataformas, BigDecimal precoDiario) {
        entidade.getTransaction().begin();

        try {
            Jogo novoJogo = new Jogo(titulo);
            List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

            for (String nomePlataforma : nomesPlataformas) {
                Plataforma plataforma = plataformaRepository.buscaPorNomeExato(nomePlataforma);
                if (plataforma == null) {
                    plataforma = new Plataforma(nomePlataforma);
                }

                JogoPlataforma jogoPlataforma = new JogoPlataforma();
                jogoPlataforma.setJogo(novoJogo);
                jogoPlataforma.setPlataforma(plataforma);
                jogoPlataforma.setPrecoDiario(precoDiario);
                jogoPlataformas.add(jogoPlataforma);
            }
            
            novoJogo.setPlataformas(jogoPlataformas);
            
            jogoRepository.salvaOuAtualiza(novoJogo);

            entidade.getTransaction().commit();
            return novoJogo;

        } catch (Exception e) {
            if (entidade.getTransaction().isActive()) {
                entidade.getTransaction().rollback();
            }
            throw new RuntimeException("Falha ao salvar o jogo: " + e.getMessage(), e);
        }
    }
}