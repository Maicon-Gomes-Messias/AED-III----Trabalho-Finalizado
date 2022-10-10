package conta_bancaria;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

public class App {

    static String mensagem;
    public static CRUD<conta_bancaria> arqconta;

    public static boolean validarEntrada(String entrada) {
        boolean error = false;

        try {
            int number = Integer.parseInt(entrada);
            error = number < 0 || number > 8;
        } catch (NumberFormatException nfe) {
            error = true;
        }

        return error;
    }

    public static String input() throws IOException {
        InputStream is = System.in;
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String entrada = br.readLine();

        return entrada;
    }

    // imprimir a saida
    public static void imprimirMenu() {

        System.out.printf("\n");
        System.out.printf("\t|--------------------------------------|\n");
        System.out.printf("\t|    No bolso!    |  Administrador     |\n");
        System.out.printf("\t|--------------------------------------|\n");
        System.out.printf("\t|       » Escolha uma das opções «     |\n");
        System.out.printf("\t|--------------------------------------|\n");
        System.out.printf("\t| (1)  Criar uma nova conta            |\n");
        System.out.printf("\t| (2)  Listar contas                   |\n");
        System.out.printf("\t| (3)  Atualizar conta                 |\n");
        System.out.printf("\t| (4)  Deletar conta                   |\n");
        System.out.printf("\t| (5)  Depositar                       |\n");
        System.out.printf("\t| (6)  Transferir                      |\n");
        System.out.printf("\t| (0)  Sair                            |\n");
        System.out.printf("\t|--------------------------------------|\n");
        System.out.printf("\n");

    }

    // metodo pra criar arquivo
    /**
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void create() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        System.out.println("\nInforme seu nome : ");
        String nome = input();
        System.out.println("\nInforme seu CPF: ");
        String cpf = input();
        System.out.println("\nInforme a cidade: ");
        String cidade = input();
        System.out.println("\nInforme seus E-MAILs():");
        String email = input();
        System.out.println("\nInforme seu Usuario: ");
        String nome_usuario = input();
        System.out.println("\nInforme sua senha: ");
        String senha = input();

        conta_bancaria conta = new conta_bancaria(-1, nome, cpf, cidade, email, nome_usuario, senha, 0, 0);

        int id = arqconta.create(conta);

        System.out.println("\nConta criada criado - ID[" + id + "]");
    }

    // metodo para listar os arquivos que foram criado
    public static boolean list() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        List<conta_bancaria> futebol = new ArrayList<conta_bancaria>();
        boolean temfutebol = true;
        futebol = arqconta.read();

        if (futebol.isEmpty()) {
            System.out.println("\nNenhum comentário cadastrado");
            temfutebol = false;
        } else {
            for (conta_bancaria f : futebol) {
                System.out.println(f.toString());
            }
        }

        return temfutebol;
    }

    // metodo para atualizar
    public static void update() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID da Conta Bancaria a ser atualizado: ");
            String entradaID = input();
            boolean error = false;

            try {
                int id = Integer.parseInt(entradaID);
                conta_bancaria futebol = arqconta.read(id);
                if (futebol != null) {
                    System.out.println("\nInforme seu nome : ");
                    String nome = input();
                    futebol.setNome(nome);

                    System.out.println("\nInforme seu CPF: ");
                    String cpf = input();
                    futebol.setCpf(cpf);

                    System.out.println("\nInforme a cidade: ");
                    String cidade = input();
                    futebol.setCidade(cidade);

                    System.out.println("\nInforme seus E-MAILs():");
                    String email = input();
                    futebol.setEmail(email);

                    System.out.println("\nInforme seu Usuario: ");
                    String nome_usuario = input();
                    futebol.setNome_usuario(nome_usuario);

                    System.out.println("\nInforme sua senha: ");
                    String senha = input();
                    futebol.setSenha(senha);

                    error = !arqconta.update(futebol);
                } else {
                    error = true;
                }
            } catch (NumberFormatException nfe) {
                error = true;
            }

            if (error) {
                System.out.println("\nOcorreu algum erro ao atualizar este Conta Bancaria");
            } else {
                System.out.println("\nConta Bancaria atualizado com sucesso!!");
            }
        }
    }

    // metodo para deletar
    public static void delete() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nInforme o ID da conta a ser deletado: ");

            String entradaId = input();

            boolean error = false;
            try {
                int id = Integer.parseInt(entradaId);
                error = !arqconta.delete(id);
            } catch (NumberFormatException nfe) {
                error = true;
            }

            if (error) {
                System.out.println("\nOcorreu algum erro na exclusão da conta");
            } else {
                System.out.println("\nConta excluída com sucesso!");
            }
        }

    }

    public static void realizar_transferencia() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temconta = list();

        if (temconta) {
            
            System.out.print("\nEscolha o ID da sua conta : ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            System.out.print("\nEscolha o ID da conta a ser transferida: ");
            String _ID2 = input();
            int ID2 = Integer.parseInt(_ID2);
            conta_bancaria conta1;
            conta_bancaria conta2;

            conta1 = arqconta.read(ID1);
            conta2 = arqconta.read(ID2);

            System.out.println("Conta 1: " + conta1);
            System.out.println("Conta 2: " + conta2);

            System.out.print("Digite o valor da sua conta a ser transferido para " + conta2.nome + " R$:  ");
            String _valor1 = input();
            float valor1 = Float.valueOf(_valor1);

            if (conta1.SaldoConta < valor1) {

                System.out.print("saldo insuficiente...");

            } else {
                conta1.SaldoConta = conta1.SaldoConta - valor1;
                conta2.SaldoConta = conta2.SaldoConta + valor1;

                System.out.print("Transferencia realizada com Sucesso... ");
                conta1.transferencia_realizada++;
                conta2.transferencia_realizada++;

                arqconta.update(conta1);
                arqconta.update(conta2);

            }
        }

    }

    public static void depositar() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temconta = list();

        if (temconta) {
            System.out.print("\nEscolha o ID da sua conta a ser feito o deposito : ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);

            conta_bancaria conta1;
            conta1 = arqconta.read(ID1);

            System.out.println("Conta 1: " + conta1);

            System.out.print("Digite o valor a ser depositado:");
            String _valor1 = input();
            float valor1 = Float.valueOf(_valor1);
            conta1.SaldoConta = conta1.SaldoConta + valor1;
            System.out.print("Deposito realizado  no valor "+valor1+ " pra conta de " + conta1.nome + " com sucesso.");
            arqconta.update(conta1);

        }

    }

    public static void escolherMetodo(int opcao) throws Exception {

        switch (opcao) {
            case 1:
                create();
                break;
            case 2:
                list();
                break;
            case 3:
                update();
                break;
            case 4:
                delete();
                break;
            case 5:
            depositar();
                break;
            case 6:
            realizar_transferencia();
                break;
            // case 5:
            // descompressao();
            // break;
            // case 6:
            // criptografar();
            // break;
            // case 7:
            // descriptografar();
            // break;
            case 0:
                System.out.println("\nObrigado. By: Maicon Gomes e Gabriel Morais");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // criando arquivo em .db
        try {
            new File("futebol.db");// .delete();
            arqconta = new CRUD<>(conta_bancaria.class.getConstructor(), "conta.db");
            String entrada;
            boolean error;
            int opcao = 0;
            do {
                imprimirMenu();
                entrada = input();
                error = validarEntrada(entrada);
                if (!error) {
                    opcao = Integer.parseInt(entrada);
                    escolherMetodo(opcao);
                } else {
                    System.out.println("\nOcorreu um erro durante a entrada de valores, tente novamente");
                    opcao = 1;
                }
            } while (opcao != 0);

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException IOe) {
            IOe.printStackTrace();
        } catch (InstantiationException ie) {
            ie.printStackTrace();
        } catch (IllegalAccessException iae) {
            iae.printStackTrace();
        } catch (InvocationTargetException ite) {
            ite.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
// menu de opções
