package org.locadora.service;

import jakarta.persistence.EntityManager;

import org.locadora.modelo.*;
import org.locadora.repositorio.*;

import java.time.LocalDate;
import java.util.*;


public class LocacaoService {

    private final EntityManager em;
    private final LocacaoRepository locacaoRepository;
    private final ClienteRepository clienteRepository;
    private final JogoPlataformaRepository jogoPlataformaRepository;

    public LocacaoService(EntityManager em) {
        this.em = em;
        this.locacaoRepository = new LocacaoRepository(em);
        this.clienteRepository = new ClienteRepository(em);
        this.jogoPlataformaRepository = new JogoPlataformaRepository(em);
    }

    public Locacao alugar(int clienteId, Map<Integer, int[]> itensParaLocar) {
        em.getTransaction().begin();

        try {
            Cliente cliente = clienteRepository.buscaPorId(clienteId);
            if (cliente == null) {
                throw new RuntimeException("Cliente com ID " + clienteId + " não foi encontrado!");
            }

            Locacao novaLocacao = new Locacao(LocalDate.now(), cliente);
            List<ItemLocacao> itensDaLocacao = new ArrayList<>();

            for (Map.Entry<Integer, Integer> item : itensParaLocar.entrySet()) {
                Integer jogoPlataformaId = item.getKey();
                Integer dias = item.getValue();

                JogoPlataforma jogoPlataforma = jogoPlataformaRepository.buscaPorId(jogoPlataformaId);
                if (jogoPlataforma == null) {
                    throw new RuntimeException("Jogo/Plataforma com ID " + jogoPlataformaId + " não encontrado!");
                }

                ItemLocacao novoItem = new ItemLocacao();
                novoItem.setJogoPlataforma(jogoPlataforma);
                novoItem.setDias(dias);
                novoItem.setLocacao(novaLocacao);
                itensDaLocacao.add(novoItem);
            }

            novaLocacao.setItens(itensDaLocacao);
            locacaoRepository.salvaOuAtualiza(novaLocacao);

            em.getTransaction().commit();
            return novaLocacao;

        } catch (Exception e) {
            // Se qualquer erro ocorrer, desfazemos a transação para não deixar dados inconsistentes
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // relança a exceção para a camada superior (Main) saber que algo deu errado
            throw new RuntimeException("Erro ao processar a locação: " + e.getMessage(), e);
        }
    }
}
