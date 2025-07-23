package org.locadora.service;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;

import java.time.LocalDate;
import java.util.*;

public class LocacaoService {

    private final EntityManager entidade;
    private final LocacaoRepository locacaoRepository;
    private final ClienteRepository clienteRepository;
    private final JogoPlataformaRepository jogoPlataformaRepository;

    public LocacaoService(EntityManager entidade) {
        this.entidade = entidade;
        this.locacaoRepository = new LocacaoRepository(entidade);
        this.clienteRepository = new ClienteRepository(entidade);
        this.jogoPlataformaRepository = new JogoPlataformaRepository(entidade);
    }
    public Locacao alugar(int clienteId, Map<Integer, int[]> itensParaLocar) {
        entidade.getTransaction().begin();

        try {
            Cliente cliente = clienteRepository.buscaPorId(clienteId);
            if (cliente == null) {
                throw new RuntimeException("Cliente com ID " + clienteId + " não foi encontrado!");
            }

            Locacao novaLocacao = new Locacao(LocalDate.now(), cliente);
            List<ItemLocacao> itensDaLocacao = new ArrayList<>();

            for (Map.Entry<Integer, int[]> item : itensParaLocar.entrySet()) {
                Integer jogoPlataformaId = item.getKey();
                int[] valores = item.getValue(); // Pega o array [dias, quantidade]
                int dias = valores[0];           // Primeiro elemento é 'dias'
                int quantidade = valores[1];     // Segundo elemento é 'quantidade'

                JogoPlataforma jogoPlataforma = jogoPlataformaRepository.buscaPorId(jogoPlataformaId);
                if (jogoPlataforma == null) {
                    throw new RuntimeException("Jogo/Plataforma com ID " + jogoPlataformaId + " não encontrado!");
                }

                ItemLocacao novoItem = new ItemLocacao();
                novoItem.setJogoPlataforma(jogoPlataforma);
                novoItem.setDias(dias);
                novoItem.setQuantidade(quantidade); // Adiciona a quantidade ao item de locação
                novoItem.setLocacao(novaLocacao);
                itensDaLocacao.add(novoItem);
            }

            novaLocacao.setItens(itensDaLocacao);
            locacaoRepository.salvaOuAtualiza(novaLocacao);

            entidade.getTransaction().commit();
            return novaLocacao;

        } catch (Exception e) {
            if (entidade.getTransaction().isActive()) {
                entidade.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao processar a locação: " + e.getMessage(), e);
        }
    }
}