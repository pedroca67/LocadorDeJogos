package org.locadora.testes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.locadora.modelo.Jogo;
import org.locadora.modelo.JogoPlataforma;
import org.locadora.modelo.Locacao;
import org.locadora.repositorio.JogoRepository;
import org.locadora.service.LocacaoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
            Integer clienteId = Integer.parseInt(scanner.nextLine());

            Map<Integer, Integer> itensParaLocar = new HashMap<>();
            boolean adicionarMaisJogos = true;

            while (adicionarMaisJogos) {
                System.out.print("\nDigite o título do jogo a ser alugado: ");
                String tituloJogo = scanner.nextLine();
                List<Jogo> jogosEncontrados = jogoRepo.buscaPorTitulo(tituloJogo);

                if (jogosEncontrados.isEmpty()) {
                    System.out.println("Nenhum jogo encontrado com esse título.");
                    continue;
                }
                Jogo jogoSelecionado = jogosEncontrados.get(0);

                System.out.println("Plataformas disponíveis para " + jogoSelecionado.getTitulo() + ":");
                for (JogoPlataforma jp : jogoSelecionado.getPlataformas()) {
                    System.out.println("ID " + jp.getId() + ". " + jp.getPlataforma().getNome() + " - Preço diário: R$ " + jp.getPrecoDiario());
                }

                System.out.print("Escolha o ID da plataforma: ");
                int jogoPlataformaId = Integer.parseInt(scanner.nextLine());

                System.out.print("Por quantos dias deseja alugar? ");
                int dias = Integer.parseInt(scanner.nextLine());

                itensParaLocar.put(jogoPlataformaId, dias);

                System.out.print("\nDeseja adicionar outro jogo? (S/N): ");
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
            System.err.println("\nERRO: Não foi possível realizar a locação.");
            System.err.println("Motivo: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
            scanner.close();
        }
    }
}