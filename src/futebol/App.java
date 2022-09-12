package futebol;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.EOFException;
import java.util.List;
import java.util.ArrayList;
import static futebol.lzw.decode;
import static futebol.lzw.encode;

public class App {

    static String mensagem;
    public static CRUD<futebol> arqfutebol;

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
        System.out.println("\n====== Campeonato mineiro ======");
        System.out.println("(1) Criar um clube");
        System.out.println("(2) Listar Clube");
        // System.out.println("(3) Atualizar Clube");
        // System.out.println("(4) Deletar Clube");
        System.out.println("(3) Realizar uma Partida");
        System.out.println("(4) compressão(LZW): ");
        System.out.println("(5) descompressão(LZW): ");
        System.out.println("(6) criptografar(Nome do Clube): ");
        System.out.println("(7) descriptografar(Nome do Clube): ");

        System.out.println("(0) Sair");
    }

    // metodo pra criar arquivo
    public static void create() throws IOException {
        System.out.println("\nInforme o nome do clube: ");
        String nome = input();
        System.out.println("\nInforme o CNPJ: ");
        String cnpj = input();
        System.out.println("\nInforme a cidade: ");
        String cidade = input();

        futebol futebol = new futebol(-1, nome, cnpj, cidade, 0, 0);

        int id = arqfutebol.create(futebol);

        System.out.println("\nClube criado - ID[" + id + "]");
    }

    // metodo para listar os arquivos que foram criado
    public static boolean list() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        List<futebol> futebol = new ArrayList<futebol>();
        boolean temfutebol = true;
        futebol = arqfutebol.read();

        if (futebol.isEmpty()) {
            System.out.println("\nNenhum comentário cadastrado");
            temfutebol = false;
        } else {
            for (futebol f : futebol) {
                System.out.println(f.toString());
            }
        }

        return temfutebol;
    }
//metodo pra realizar partida
    public static void realizar_partita() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temfutebol = list();

        if (temfutebol) {
            System.out.print("\nEscolha o ID do primeiro clube: ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            System.out.print("\nEscolha o ID do segundo clube: ");
            String _ID2 = input();
            int ID2 = Integer.parseInt(_ID2);
            futebol time1;
            futebol time2;

            time1 = arqfutebol.read(ID1);
            time2 = arqfutebol.read(ID2);

            System.out.println("TIME 1: " + time1);
            System.out.println("TIME 2: " + time2);

            System.out.print("Digite quantos gols fez o " + time1.nome + ": ");
            String _gols1 = input();
            int gols1 = Integer.parseInt(_gols1);
            System.out.print("Digite quantos gols fez o " + time2.nome + ": ");
            String _gols2 = input();
            int gols2 = Integer.parseInt(_gols2);

            if (gols1 > gols2) {
                System.out.println(time1.nome + " VENCEU!!! ");
                time1.ponto += 3;
            } else if (gols2 > gols1) {
                System.out.println(time2.nome + " VENCEU!!! ");
                time2.ponto += 3;
            } else {
                System.out.println(" EMPATE ");
                time1.ponto++;
                time2.ponto++;
            }
            time1.partida_jogada++;
            time2.partida_jogada++;

            arqfutebol.update(time1);
            arqfutebol.update(time2);

        }

    }

    // metodo para atualizar
    public static void update() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID do comentário a ser atualizado: ");
            String entradaID = input();
            boolean error = false;

            try {
                int id = Integer.parseInt(entradaID);
                futebol futebol = arqfutebol.read(id);
                if (futebol != null) {
                    System.out.println("\nInforme o nome : ");
                    String nome = input();
                    futebol.setNome(nome);

                    System.out.println("\nInforme o cnpj: ");
                    String cnpj = input();
                    futebol.setCnpj(cnpj);

                    System.out.println("\nInforme a cidade: ");
                    String cidade = input();
                    futebol.setCidade(cidade);

                    error = !arqfutebol.update(futebol);
                } else {
                    error = true;
                }
            } catch (NumberFormatException nfe) {
                error = true;
            }

            if (error) {
                System.out.println("\nOcorreu algum erro ao atualizar este comentário");
            } else {
                System.out.println("\nComentário atualizado com sucesso!!");
            }
        }
    }

    // metodo para deletar
    public static void delete() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nInforme o ID a ser deletado: ");

            String entradaId = input();

            boolean error = false;
            try {
                int id = Integer.parseInt(entradaId);
                error = !arqfutebol.delete(id);
            } catch (NumberFormatException nfe) {
                error = true;
            }

            if (error) {
                System.out.println("\nOcorreu algum erro na exclusão do comentário");
            } else {
                System.out.println("\nComentário excluído com sucesso!");
            }
        }

    }
    //metodo de compressao por nome cnpj e cidade

    public static void compressao() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID do clube a ser feita a compressão: ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            futebol time1;
            time1 = arqfutebol.read(ID1);

            if (time1 != null) {
                FileWriter arq = new FileWriter("nomeantigoCompressaoX.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
                gravarArq.println("Palavra original:\n\n" + time1.nome + "\n" + time1.cnpj + "\n" + time1.cidade + "\n");
                List<Integer> compressed = encode(time1.nome + time1.cnpj + time1.cidade);
                gravarArq.println("\nPalavra comprimida :" + compressed);
                System.out.println("PALAVRA comprimida com sucesso...verifique no arquivo txt.");
                arq.close();
            }

        }

        // ------------------------ compressao por dados-------------------------------------//
        // System.out.println(time1);
        // int opc;
        // System.out.println("Qual dado deseja ser feita a compressão: ");
        // System.out.println("(1) nome do clube: ");
        // System.out.println("(2) CNPJ: ");
        // System.out.println("(3) cidade: ");
        // opc = console.nextInt();

        // String decompressed = decode(compressed);
        // System.out.println(decompressed);

        // if (opc == 1) {
        // FileWriter arq = new FileWriter("nomeantigoCompressaoX.txt");
        // PrintWriter gravarArq = new PrintWriter(arq);
        // gravarArq.println("\nPalavra original : " + time1.nome);
        // List<Integer> compressed = encode(time1.nome);
        // gravarArq.println("\nPalavra comprimida : "+compressed);
        // System.out.println("NOME DO CLUBE comprimida com sucesso...verifique no
        // arquivo txt.");
        // // String decompressed = decode(compressed);
        // // System.out.println(decompressed);
        // arq.close();

        // }
        // else if (opc == 2) {
        // FileWriter arq = new FileWriter("nomeantigoCompressaoX.txt");
        // PrintWriter gravarArq = new PrintWriter(arq);
        // gravarArq.println("\nPalavra original : " + time1.cnpj);
        // List<Integer> compressed = encode(time1.cnpj);
        // gravarArq.println("\nPalavra comprimida : "+compressed);
        // System.out.println("CNPJ comprimida com sucesso...verifique no arquivo
        // txt.");
        // // String decompressed = decode(compressed);
        // // System.out.println(decompressed);
        // arq.close();;
        // } else if (opc == 3) {
        // FileWriter arq = new FileWriter("nomeantigoCompressaoX.txt");
        // PrintWriter gravarArq = new PrintWriter(arq);
        // gravarArq.println("\nPalavra original : " + time1.cidade);
        // List<Integer> compressed = encode(time1.cidade);
        // gravarArq.println("\nPalavra comprimida : "+compressed);
        // System.out.println("CIDADE comprimida com sucesso...verifique no arquivo
        // txt.");
        // // String decompressed = decode(compressed);
        // // System.out.println(decompressed);
        // arq.close();
        // }

    }
    // metodo pra descomprimir dado realizado pelo usuario.ela recebe a palavra comprimida no menu de opções 
    // e realiza da descompressao.
    public static void descompressao() throws IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID do clube a ser feita a descompressão: ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            futebol time1;
            time1 = arqfutebol.read(ID1);

            if (time1 != null) {

                FileWriter arquivo = new FileWriter("nomeantigoCompressaoX.txt");
                PrintWriter gravarArq = new PrintWriter(arquivo);
                List<Integer> compressed = encode(time1.nome + time1.cnpj + time1.cidade);
                String decompressed = decode(compressed);
                gravarArq.println("Palavra Descomprimida:\n " + decompressed);
                System.out.println("PALAVRA descomprimida com sucesso...verifique no arquivo txt.");
                arquivo.close();

            }

        }
    }
    //metodo pra criptografar e descriptografar o nome do clube digitado pelo usuario 
    public static void criptografar() throws Exception, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID do clube a ser feita a criptografia: ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            
            futebol time1;

            time1 = arqfutebol.read(ID1);
        
           
            if (time1 != null) {
                try {
                    FileWriter arquivo = new FileWriter("arquivo_cripitografado.txt");
                    PrintWriter gravarArq = new PrintWriter(arquivo);
                    AES aes = new AES();
                    aes.init();
                    gravarArq.println("\nMensagem original: " + time1.nome);
                    gravarArq.println("\n");
                    String mensagem_criptografada = aes.encrypt(time1.nome);
                    String mensagem_descriptografada = aes.decrypt(mensagem_criptografada);
                    gravarArq.println("\nMensagem Criptografada :" + mensagem_criptografada);
                    gravarArq.println("\n");
                    time1.nome = mensagem_criptografada;
                    arqfutebol.update(time1);
                    mensagem = mensagem_descriptografada;                
                    //gravarArq.println("Mensagem Descriptografada: " + mensagem_descriptografada);
                    System.out.println("PALAVRA criptografada com sucesso...verifique no arquivo txt.");
                
                    arquivo.close();
                    
                } catch (EOFException erro) {
                    System.out.println("erro com arquivo");
                }
           
                
            }
        }

    }

    

//trocado de void pra string
    public static void descriptografar() throws Exception, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {

        boolean temfutebol = list();
        if (temfutebol) {
            System.out.println("\nEscolha o ID do clube a ser feita a descriptografia: ");
            String _ID1 = input();
            int ID1 = Integer.parseInt(_ID1);
            futebol time1;
            time1 = arqfutebol.read(ID1);

            if (time1 != null) {

                FileWriter arquivo = new FileWriter("arquivo_descripitografado.txt");
                PrintWriter gravarArq = new PrintWriter(arquivo);
                
                gravarArq.println("Mensagem Descriptografada: "+ mensagem );
                gravarArq.println("\n");
                System.out.println("PALAVRA descriptografada com sucesso...verifique no arquivo txt.");
                time1.nome= mensagem;
                arqfutebol.update(time1);
                arquivo.close();
            }
        }


    }

    // menu de opções
    public static void escolherMetodo(int opcao) throws Exception {
   
        switch (opcao) {
            case 1:
                create();
                break;
            case 2:
                list();
                break;
            case 3:
                realizar_partita();
                break;
            case 4:
                compressao();
                break;
            case 5:
                descompressao();
                break;
            case 6:
                criptografar();
                break;
            case 7:
            descriptografar();
                break;
            case 0:
                System.out.println("\nObrigado. By: Maicon Gomes");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        // criando arquivo em .db
        try {
            new File("futebol.db");// .delete();
            arqfutebol = new CRUD<>(futebol.class.getConstructor(), "futebol.db");
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