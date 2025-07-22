package org.locadora.testes;

import jakarta.persistence.*;
import org.locadora.modelo.*;
import org.locadora.repositorio.*;
import java.math.*;
import java.time.*;
import java.util.*;


public class LocacaoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("LocadorDeJogos");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Scanner scanner = new Scanner(System.in);

        ClienteRepository clienteRepo = new ClienteRepository(em);
        JogoRepository jogoRepo = new JogoRepository(em);
        LocacaoRepository locacaoRepo = new LocacaoRepository(em);

        try {
            System.out.println("--- Realizar Nova Locação ---");

            // 1. Selecionar o cliente
            System.out.print("Digite o ID do cliente: ");
            Integer clienteId = Integer.parseInt(scanner.nextLine());
            Cliente cliente = clienteRepo.buscaPorId(clienteId);

            if (cliente == null) {
                System.out.println("Cliente não encontrado!");
                return;
            }

            System.out.println("Cliente selecionado: " + cliente.getNome());

            // 2. Criar a locação
            Locacao novaLocacao = new Locacao(LocalDate.now(), cliente);
            List<ItemLocacao> itensDaLocacao = new ArrayList<>();
            boolean adicionarMaisJogos = true;
            BigDecimal valorTotal = BigDecimal.ZERO;

            while (adicionarMaisJogos) {
                // 3. Selecionar o jogo
                System.out.print("Digite o título do jogo a ser alugado: ");
                String tituloJogo = scanner.nextLine();
                List<Jogo> jogosEncontrados = jogoRepo.buscaPorTitulo(tituloJogo);

                if (jogosEncontrados.isEmpty()) {
                    System.out.println("Nenhum jogo encontrado com esse título.");
                    continue;
                }

                // Supondo que o primeiro jogo da lista é o correto
                Jogo jogoSelecionado = jogosEncontrados.get(0);
                System.out.println("Jogo selecionado: " + jogoSelecionado.getTitulo());

                // 4. Listar e selecionar a plataforma
                List<JogoPlataforma> plataformasDisponiveis = jogoSelecionado.getPlataformas();
                System.out.println("Plataformas disponíveis para " + jogoSelecionado.getTitulo() + ":");
                for (int i = 0; i < plataformasDisponiveis.size(); i++) {
                    JogoPlataforma jp = plataformasDisponiveis.get(i);
                    System.out.println((i + 1) + ". " + jp.getPlataforma().getNome() + " - Preço diário: R$ " + jp.getPrecoDiario());
                }

                System.out.print("Escolha o número da plataforma: ");
                int escolhaPlataforma = Integer.parseInt(scanner.nextLine()) - 1;
                JogoPlataforma jogoPlataformaSelecionada = plataformasDisponiveis.get(escolhaPlataforma);

                // 5. Definir a quantidade de dias
                System.out.print("Por quantos dias deseja alugar este jogo? ");
                int dias = Integer.parseInt(scanner.nextLine());

                // 6. Criar o item da locação
                ItemLocacao novoItem = new ItemLocacao();
                novoItem.setJogoPlataforma(jogoPlataformaSelecionada);
                novoItem.setDias(dias);
                novoItem.setLocacao(novaLocacao);
                itensDaLocacao.add(novoItem);

                // 7. Calcular o subtotal do item e adicionar ao valor total
                valorTotal = valorTotal.add(jogoPlataformaSelecionada.getPrecoDiario().multiply(new BigDecimal(dias)));

                System.out.print("Deseja adicionar outro jogo à locação? (S/N): ");
                adicionarMaisJogos = scanner.nextLine().trim().equalsIgnoreCase("S");
            }

            novaLocacao.setItens(itensDaLocacao);

            // 8. Salvar a locação
            tx.begin();
            locacaoRepo.salvaOuAtualiza(novaLocacao);
            tx.commit();

            System.out.println("\n--- Locação Realizada com Sucesso! ---");
            System.out.println("Cliente: " + novaLocacao.getCliente().getNome());
            System.out.println("Data: " + novaLocacao.getData());
            System.out.println("Itens alugados:");
            for(ItemLocacao item : novaLocacao.getItens()) {
                System.out.println("- Jogo: " + item.getJogoPlataforma().getJogo().getTitulo() +
                        " (" + item.getJogoPlataforma().getPlataforma().getNome() + ")" +
                        " por " + item.getDias() + " dias.");
            }
            System.out.println("Valor Total da Locação: R$ " + valorTotal);


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