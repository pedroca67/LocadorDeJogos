package org.locadora.service;

import jakarta.persistence.EntityManager;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;

import java.time.LocalDate;
import java.util.*;

public class LocacaoService {

    private final EntityManager e1;
    private final LocacaoRepository locacaoRepo;
    private final ClienteRepository clienteRepo;
    private final JogoPlataformaRepository jogoPlataformaRepo;

    public LocacaoService(EntityManager e1) {
        this.e1 = e1;
        this.locacaoRepo = new LocacaoRepository(e1);
        this.clienteRepo = new ClienteRepository(e1);
        this.jogoPlataformaRepo = new JogoPlataformaRepository(e1);
    }
    public Locacao alugar(int clienteId, Map<Integer, int[]> itensParaLocar) {
        e1.getTransaction().begin();

        try {
            Cliente cliente = clienteRepo.buscaPorId(clienteId);
            if (cliente == null) {
                throw new RuntimeException("Cliente com ID " + clienteId + " não foi encontrado!");
            }

            Locacao novaLocacao = new Locacao(LocalDate.now(), cliente);

            for (Map.Entry<Integer, int[]> item : itensParaLocar.entrySet()) {
                Integer idDeJogoPlataforma = item.getKey();
                int[] valores = item.getValue();
                int dias = valores[0];
                int quantidade = valores[1];

                JogoPlataforma jogoPlataforma = jogoPlataformaRepo.buscaPorId(idDeJogoPlataforma);
                if (jogoPlataforma == null) {
                    throw new RuntimeException("Jogo/Plataforma com ID " + idDeJogoPlataforma + " não encontrado!");
                }

                ItemLocacao novo = new ItemLocacao();
                novo.setJogoPlataforma(jogoPlataforma);
                novo.setDias(dias);
                novo.setQuantidade(quantidade);

                novaLocacao.adicionarItem(novo);

            }

            locacaoRepo.salvar(novaLocacao);

            e1.getTransaction().commit();
            return novaLocacao;

        } catch (Exception e) {
            if (e1.getTransaction().isActive()) {
                e1.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao processar a locação: " + e.getMessage(), e);
        }
    }
}