package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int opcMenu;

        List<Aposta> apostas = new ArrayList<>();
        GerenciaApostadores gerenciaApostadores = new GerenciaApostadores();
        GerenciaAposta gerenciaAposta = new GerenciaAposta();
        List<Apostador> apostadores = new ArrayList<>();
        Premiacao premiacao = new Premiacao();

        
        while (true) {
            menuPrincipal();
            System.out.println("Selecione a opção desejada: ");
            String opc = in.nextLine().toUpperCase();
            if (opc.equals("A")) {
                processarApostador(opc, apostas, gerenciaApostadores, gerenciaAposta, apostadores, premiacao);
            } else if (opc.equals("O")) {
                processarOperador(opc, apostas, gerenciaApostadores, gerenciaAposta, apostadores, premiacao);
            }
        }
    }

    public static void menuPrincipal() {
        String menu = """
                ### BEM VINDO À MEGA-SENA 2.0 ###\n
                Por favor, indetifique-se:\n
                A - Apostador
                O - Operador\n

                """;
        System.out.println(menu);
    }

    public static void menuApostador(Premiacao premiacao) {
        String menu = String.format("""
                ### PERFIL DE USUÁRIO ###\n

                !!! PRÊMIO DA NOITE: R$  %d !!!\n
                (A premiação muda automaticamente a cada sorteio)

                Operações Disponíveis:\n
                1 - Cadastrar Apostador
                2 - Cadastrar Aposta
                3 - Remover Aposta
                4 - Verificar Aposta
                0 - Sair\n

                Selecione uma Opção:
                """, premiacao.getPremio());

        System.out.println(menu);
    }

    public static void menuOperador(List<Aposta> apostas) {

        System.out.println("### PERFIL DE OPERADOR ###\n");
        System.out.println("Lista de apostas:\n");

        for (Aposta aposta : apostas) {
            System.out.println(aposta.toString());
        }

        String menu = """
                Operações Disponíveis:\n

                1 - Procurar Apostador
                2 - Executar Sorteio
                0 - Sair\n

                Selecione uma Opção:
                """;

        System.out.println(menu);
    }

    public static void menuTipoAposta() {
        String menu = """
                Operações Disponíveis:\n

                1 - Aposta manual
                2 - Surpresinha

                Selecione o tipo de aposta:
                """;

        System.out.println(menu);
    }

    public static void processarApostador(String opcUser, List<Aposta> apostas,
            GerenciaApostadores gerenciaApostadores, GerenciaAposta gerenciaAposta, List<Apostador> apostadores, Premiacao premiacao) {
        Scanner in = new Scanner(System.in);
        int opcMenu;

        while (true) {
            menuApostador(premiacao);
            opcMenu = in.nextInt();
            in.nextLine();  //limpa o buffer

            if (opcMenu == 0) {
                System.out.println("Saindo do menu de Apostador...");
                break;
            }
            switch (opcMenu) {
                case 1:
                    System.out.println("Digite seu nome: ");
                    String nome = in.nextLine();
                    System.out.println("Digite seu CPF: ");
                    long cpf = in.nextLong();
                    in.nextLine();  //limpa o buffer

                    if (!gerenciaApostadores.apostadorExiste(apostadores, cpf)) {
                        Apostador novoApostador = new Apostador(nome, cpf);
                        gerenciaApostadores.cadastraApostador(novoApostador);
                        System.out.println("Apostador cadastrado com êxito.");
                    } else {
                        Apostador apostadorExistente = gerenciaApostadores.getApostador(cpf);
                        System.out.println("Apostador já cadastrado: " + apostadorExistente);
                    }
                    break;

                case 2:

                    System.out.println("Digite seu CPF: ");
                    cpf = in.nextLong();

                    // limpando o buffer
                    in.nextLine();

                    Apostador apostadorExistente = gerenciaApostadores.getApostador(cpf);

                    if (apostadorExistente == null) {
                        String resp;
                        System.out.println(
                                "Você ainda não foi cadastrado. Gostaria de criar um cadastro? [s/n]");
                        resp = in.nextLine();
                        if (resp.equals("s")) {
                            System.out.println("Digite seu nome: ");
                            nome = in.nextLine();
                            // VERIFICAR SE O NOME E CPF ESTÃO CORRETOS!!!
                            Apostador novoApostador = new Apostador(nome, cpf);
                            gerenciaApostadores.cadastraApostador(novoApostador);
                            apostadorExistente = novoApostador;
                            System.out.println("Apostador cadastrado com êxito");
                        } else {
                            System.out.println("Apenas usuários cadastrados podem fazer apostas.");
                            break;
                        }
                    } else {
                        System.out.println("Bem vindo, " + apostadorExistente.getNome() + "\n");

                        boolean continuarApostando = true;

                        menuTipoAposta();

                        int opcAposta = in.nextInt();
                        if (opcAposta == 1) {
                            List<Integer> numerosApostados = new ArrayList<>();
                            while (continuarApostando) {
                                System.out.println("Digite 5 números para sua aposta (entre 1 e 50): ");
                                for (int i = 0; i < 5; i++) {
                                    int numero = in.nextInt();
                                    numerosApostados.add(numero);
                                }

                                try {
                                    gerenciaAposta.validarAposta(numerosApostados);
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }

                                int idAPosta = gerenciaAposta.getNextId();
                                Aposta novaAposta = new Aposta(apostadorExistente, idAPosta,
                                        numerosApostados);
                                gerenciaAposta.registrarAposta(novaAposta);
                                apostadorExistente.adicionarAposta(novaAposta); // adiciona a aposta à lista do apostador

                                System.out.println("Aposta registrada com êxito.\n");
                                System.out.println("Seus números da sorte são: " + numerosApostados
                                        + "\n Que a sorte esteja com você!");

                                System.out.println("Deseja realizar outra aposta? [s/n]");
                                in.nextLine(); // limpa o buffer
                                String resp = in.nextLine();
                                if (resp.equals("n")) {
                                    continuarApostando = false;
                                }
                            }
                        } else if (opcAposta == 2) {
                            int idApostaSurpresinha = gerenciaAposta.getNextId(); // preciso pegar o id
                                                                                  // antes para lista o
                                                                                  // número da sorte

                            gerenciaAposta.surpresinha(apostadorExistente, idApostaSurpresinha);
                            System.out.println(
                                    "Supresinha realizada com êxito! Apenas o Operador sabe seus números.");
                            System.out.println("Aguarde o anúncio do Operador. Boa sorte!");
                            System.out.println("Seu ID de aposta é: " + idApostaSurpresinha);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Digite seu CPF: ");
                    cpf = in.nextLong();

                    in.nextLine(); // limpando o buffer

                    Apostador apostador = gerenciaApostadores.getApostador(cpf);

                    if (apostador != null) {
                        System.out.println("Digite o ID da Aposta que deseja remover: ");
                        int idAPosta = in.nextInt();
                        in.nextLine(); // limpa o buffer

                        boolean apostaEncontrada = false;
                        for (Aposta aposta : apostas) {
                            if (aposta.getIdAposta() == idAPosta
                                    && aposta.getApostador().equals(apostador)) { // se o apostador é dono
                                                                                  // da aposta que deseja
                                                                                  // excluir
                                apostaEncontrada = true;
                                System.out
                                        .println("Deseja excluir a aposta abaixo?\n" + aposta + "\n[s/n]?");
                                String resp = in.nextLine();

                                if (resp.equals("s")) {
                                    apostas.remove(aposta);
                                    System.out.println("A aposta foi removida com êxito.");
                                } else {
                                    System.out.println("A aposta não foi removida.");
                                }
                                break;
                            }
                        }
                        if (!apostaEncontrada) {
                            System.out.println("Nenhuma aposta encontrada para o apostador "
                                    + apostador.getNome() + " com o ID fornecido.");
                        }
                    } else {
                        System.out.println("O apostador não foi encontrado");
                    }
                    break;

                case 4:
                    System.out.println("Digite seu CPF: ");
                    cpf = in.nextLong();

                    apostador = gerenciaApostadores.getApostador(cpf);

                    if (apostador != null) {
                        System.out.println(
                                "Apostas feitas por " + gerenciaApostadores.getApostador(cpf) + ": ");
                        List<Aposta> apostasDoApostador = gerenciaAposta.getApostasDoApostador(apostador);

                        if (!apostasDoApostador.isEmpty()) {
                            for (Aposta aposta : apostasDoApostador) {
                                System.out.println(aposta.toString());
                            }
                        } else {
                            System.out.println("Nenhuma aposta encontrada para este apostador.");
                        }
                    } else {
                        System.out.println("Apostador não encontrado.");
                    }
                    break;

                default:
                    System.out.println("Digite um número válido.");
                    break;
            }
        }
    }

    public static void processarOperador(String opcUser, List<Aposta> apostas,
            GerenciaApostadores gerenciaApostadores, GerenciaAposta gerenciaAposta, List<Apostador> apostadores, Premiacao premiacao) {
        Scanner in = new Scanner(System.in);
        int opcMenu;

        while (true) {
            menuOperador(apostas);
            opcMenu = in.nextInt();
            in.nextLine(); //limpa o buffer
            final int password = 1234;
            boolean senhaCorreta = false;

            if (opcMenu == 0) {
                System.out.println("Saindo do programa...");
                break;
            }

            while (!senhaCorreta) {
                System.out.println("Digite a senha do operador: ");
                int senha = in.nextInt();

                if (senha == 1234) {
                    senhaCorreta = true;
                    break;
                }
                System.out.println("Senha incorreta.");
            }

            switch (opcMenu) {
                case 1:
                    System.out.println("Digite o CPF do Apostador: ");
                    long cpf = in.nextLong();

                    Apostador apostador = gerenciaApostadores.getApostador(cpf);

                    if (apostador != null) {
                        System.out.println(
                                "Apostas feitas por " + gerenciaApostadores.getApostador(cpf) + ": ");
                        List<Aposta> apostasDoApostador = gerenciaAposta.getApostasDoApostador(apostador);

                        if (!apostasDoApostador.isEmpty()) {
                            for (Aposta aposta : apostasDoApostador) {
                                System.out.println(aposta.toString());
                            }
                        } else {
                            System.out.println("Nenhuma aposta encontrada para este apostador.");
                        }
                    } else {
                        System.out.println("Apostador não encontrado.");
                    }
                    break;

                case 2:
                    System.out.println("Confirmar a execução do sorteio:[s/n]");
                    String resp = in.next();
                    in.nextLine();
                    if (resp.equals("s")) {
                        Sorteio sorteio = new Sorteio();
                        boolean vencedoresEncontrados = false;
                        int rodadas = 0;

                        List<Integer> vencedores = new ArrayList<>(); // inicializa lista vencedores

                        while (!vencedoresEncontrados && rodadas <= 25) {
                            List<Integer> numerosSorteados = sorteio.sortear();
                            rodadas++;
                            System.out.println("Rodada de N° " + rodadas);
                            System.out.println("~~~ NÚMEROS SORTEADOS ~~~\n" + numerosSorteados);

                            vencedores = sorteio.verificaVencedores(apostas);

                            if (!vencedores.isEmpty()) {
                                vencedoresEncontrados = true;
                                System.out.println(" ### VENCEDORES DA MEGA SENA ###\n"); // 5.a

                                double premioDividido = premiacao.dividirPremio(premiacao.getPremio(),
                                        vencedores.size());

                                for (Integer idVencedor : vencedores) {
                                    System.out.println("ID Vencedor: " + idVencedor
                                            + " - Prêmio Recebido: R$ " + premioDividido);

                                    try {
                                        Thread.sleep(2000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                System.out.println("Não houve vencedores nesta rodada.");

                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (vencedoresEncontrados) {
                            System.out.println("MÉTRICAS DO SORTEIO:\n");

                            // 5.b
                            System.out.println("Rodadas Realizadas: " + rodadas);

                            // 5.c
                            System.out.println("Quantidade de Apostas Vencedoras: "
                                    + sorteio.quantidadeApostasVencedoras(vencedores));

                            // 5.d
                            List<Integer> vencedoresOrdenados = sorteio
                                    .ordenarApostasVencedoras(vencedores);
                            System.out.println(
                                    "Lista dos Vencedores (Ordem Alfabética): " + vencedoresOrdenados);

                            // 5.e
                            List<Integer> numApostadosOrdenados = gerenciaAposta.quantNumApostados();
                            System.out.println("N° apostado     Qnt apostas");
                            for (Integer num : numApostadosOrdenados) {
                                int quantApostas = Collections.frequency(apostas, num);
                                System.out.println(String.format("%-10d     %d", num, quantApostas));
                            }

                        } else {
                            System.out.println("Nenhum vencedor foi encontrado após 25 rodadas.");
                        }
                    } else {
                        System.out.println("A execução do sorteio foi cancelada");
                    }
                    break;

                default:
                    System.out.println("Digite um número válido.");
                    break;
            }
        }

    }
}

