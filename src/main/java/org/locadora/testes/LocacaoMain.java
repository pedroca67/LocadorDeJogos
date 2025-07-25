package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;
import org.locadora.service.*;

import java.util.*;
import java.util.stream.*;

public class LocacaoMain {
    public static void main(String[] args) {
        EntityManagerFactory a2 = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager a1 = a2.createEntityManager();
        Scanner s = new Scanner(System.in);

        JogoRepository jogoRepo = new JogoRepository(a1);
        LocacaoService locacaoService = new LocacaoService(a1);

        try {
            System.out.println("--- Realizar Nova Locação ---");
            System.out.print("Digite o ID do cliente: ");
            int clienteId;
            try {
                clienteId = Integer.parseInt(s.nextLine().trim());
                if (clienteId <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.err.println("ID do cliente inválido.");
                return;
            }

            // ALTERAÇÃO 1: O Map agora guarda um array de inteiros [dias, quantidade]
            Map<Integer, int[]> itensParaLocar = new HashMap<>();
            boolean adicionarJogo = true;

            while (adicionarJogo) {
                System.out.print("\nDigite o título do jogo a ser alugado: ");
                String tituloJogo = s.nextLine().trim();

                List<Jogo> jogosEncontrados = jogoRepo.buscaPorTitulo(tituloJogo);
                if (jogosEncontrados.isEmpty()) {
                    System.out.println("Nenhum jogo encontrado com esse título.");
                    continue;
                }

                Jogo jogoSelecionado = jogosEncontrados.get(0);
                System.out.println("Plataformas disponíveis:");
                for (JogoPlataforma jp : jogoSelecionado.getPlataformas()) {
                    System.out.println("ID " + jp.getId() + ": " + jp.getPlataforma().getNome() + " - R$ " + jp.getPrecoDiario());
                }

                System.out.print("Escolha o ID da plataforma: ");
                int idPlataforma;
                try {
                    idPlataforma = Integer.parseInt(s.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.err.println("ID invalido.");
                    continue;
                }

                List<Integer> idsValidos = jogoSelecionado.getPlataformas()
                        .stream()
                        .map(JogoPlataforma::getId)
                        .collect(Collectors.toList());

                if (!idsValidos.contains(idPlataforma)) {
                    System.err.println("ID de plataforma invalido! Por favor, tente novamente.");
                    continue;
                }

                System.out.print("Por quantos dias deseja alugar? ");
                int dias;
                try {
                    dias = Integer.parseInt(s.nextLine().trim());
                    if (dias <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.err.println("Quantidade de dias inválida.");
                    continue;
                }
                
                // ALTERAÇÃO 2: Pergunta a quantidade
                System.out.print("Quantas cópias deseja alugar? ");
                int quantidade;
                try {
                    quantidade = Integer.parseInt(s.nextLine().trim());
                    if (quantidade <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.err.println("Quantidade inválida.");
                    continue;
                }

                // ALTERAÇÃO 3: Adiciona o array com [dias, quantidade] ao map
                itensParaLocar.put(idPlataforma, new int[]{dias, quantidade});

                System.out.print("Deseja adicionar outro jogo? (S/N): ");
                adicionarJogo = s.nextLine().trim().equalsIgnoreCase("S");
            }

            if (itensParaLocar.isEmpty()) {
                System.out.println("Nenhum item adicionado. Locação cancelada.");
                return;
            }

            // ALTERAÇÃO 4: A chamada ao serviço agora passa o novo Map
            Locacao locacaoRealizada = locacaoService.alugar(clienteId, itensParaLocar);
            System.out.println("\n--- Locação Realizada com Sucesso! ---");
            System.out.println("ID da Locação: " + locacaoRealizada.getId());
            System.out.println("Cliente: " + locacaoRealizada.getCliente().getNome());

        } catch (Exception e) {
            System.err.println("Problema: " + e.getMessage());
        } finally {
            a1.close();
            a2.close();
            s.close();
        }
    }
}
