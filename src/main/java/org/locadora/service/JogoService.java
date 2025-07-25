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

    private final EntityManager e1;
    private final JogoRepository jogoRepo;
    private final PlataformaRepository plataformaRepo;

    public JogoService(EntityManager e1) {
        this.e1 = e1;
        this.jogoRepo = new JogoRepository(e1);
        this.plataformaRepo = new PlataformaRepository(e1);
    }

    public Jogo salvar(String titulo, List<String> nomesPlataformas, BigDecimal precoDiario) {
        e1.getTransaction().begin();

        try {
            Jogo novoJogo = new Jogo(titulo);
            List<JogoPlataforma> jogoPlataformas = new ArrayList<>();

            for (String nomePlataforma : nomesPlataformas) {
                Plataforma plataforma = plataformaRepo.buscaPorNomeExato(nomePlataforma);
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
            
            jogoRepo.salvaOuAtualiza(novoJogo);

            e1.getTransaction().commit();
            return novoJogo;

        } catch (Exception e) {
            if (e1.getTransaction().isActive()) {
                e1.getTransaction().rollback();
            }
            throw new RuntimeException("Falha ao salvar o jogo: " + e.getMessage(), e);
        }
    }
}