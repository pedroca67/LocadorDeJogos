package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;
import org.locadora.service.*;

import java.util.*;
import java.util.stream.*;

public class LocacaoMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager em = emf.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        JogoRepository jogoRepo = new JogoRepository(em);
        LocacaoService locacaoService = new LocacaoService(em);

        try {
            System.out.println("--- Realizar Nova Locação ---");
            System.out.print("Digite o ID do cliente: ");
            int clienteId;
            try {
                clienteId = Integer.parseInt(scanner.nextLine().trim());
                if (clienteId <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.err.println("ID do cliente inválido.");
                return;
            }

            Map<Integer, Integer> itensParaLocar = new HashMap<>();
            boolean adicionarMaisJogos = true;

            while (adicionarMaisJogos) {
                System.out.print("\nDigite o título do jogo a ser alugado: ");
                String tituloJogo = scanner.nextLine().trim();

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
                int jogoPlataformaId;
                try {
                    jogoPlataformaId = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.err.println("ID inválido.");
                    continue;
                }

                List<Integer> idsValidos = jogoSelecionado.getPlataformas()
                        .stream()
                        .map(JogoPlataforma::getId)
                        .collect(Collectors.toList());

                if (!idsValidos.contains(jogoPlataformaId)) {
                    System.err.println("ERRO: ID de plataforma inválido! Por favor, tente novamente.");
                    continue;
                }

                System.out.print("Por quantos dias deseja alugar? ");
                int dias;
                try {
                    dias = Integer.parseInt(scanner.nextLine().trim());
                    if (dias <= 0) throw new NumberFormatException();
                } catch (NumberFormatException e) {
                    System.err.println("Quantidade de dias inválida.");
                    continue;
                }

                itensParaLocar.put(jogoPlataformaId, dias);

                System.out.print("Deseja adicionar outro jogo? (S/N): ");
                adicionarMaisJogos = scanner.nextLine().trim().equalsIgnoreCase("S");
            }

            if (itensParaLocar.isEmpty()) {
                System.out.println("Nenhum item adicionado. Locação cancelada.");
                return;
            }

            Locacao locacaoRealizada = locacaoService.alugar(clienteId, itensParaLocar);
            System.out.println("\n--- Locação Realizada com Sucesso! ---");
            System.out.println("ID da Locação: " + locacaoRealizada.getId());
            System.out.println("Cliente: " + locacaoRealizada.getCliente().getNome());

        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }
}
