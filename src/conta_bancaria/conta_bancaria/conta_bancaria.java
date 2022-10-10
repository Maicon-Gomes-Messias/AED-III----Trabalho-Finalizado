package conta_bancaria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.IOException;

import java.util.Scanner;

//classe conta
public class conta_bancaria implements Registro {
    //public static Object lzw;
    Scanner console = new Scanner(System.in);
    protected int id;
    protected String nome;
    protected String email;
    protected String nome_usuario;
    protected String senha;
    protected String cpf;
    protected String cidade;
    protected int transferencia_realizada = 0;
    protected float SaldoConta;


    // construtor
    public conta_bancaria(int id, String nome, String email, String nome_usuario, String senha, String cpf, String cidade,
            int transferencia_realizada, float SaldoConta) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nome_usuario = nome_usuario;
        this.senha = senha;
        this.cpf = cpf;
        this.cidade = cidade;
        this.transferencia_realizada = transferencia_realizada;
        this.SaldoConta = SaldoConta;
        

    }

    // construtor
    public conta_bancaria() {
        this.id = 0;
        this.nome = "";
        this.email = "";
        this.nome_usuario = "";
        this.senha = "";
        this.cpf = "";
        this.cidade = "";
        this.transferencia_realizada = 0;
        this.SaldoConta = 0;
  
    }

    // criar o arquivo em .db

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        dos.writeInt(id);
        dos.writeUTF(nome);
        dos.writeUTF(email);
        dos.writeUTF(nome_usuario);
        dos.writeUTF(senha);
        dos.writeUTF(cpf);
        dos.writeUTF(cidade);
        dos.writeInt(transferencia_realizada);
        dos.writeFloat(SaldoConta);


        return baos.toByteArray();
    }

    public void fromByteArray(byte[] ba) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        id = dis.readInt();
        nome = dis.readUTF();
        email = dis.readUTF();
        nome_usuario = dis.readUTF();
        senha = dis.readUTF();
        cpf = dis.readUTF();
        cidade = dis.readUTF();
        transferencia_realizada = dis.readInt();
        SaldoConta = dis.readFloat();
   
    }

    // atributos utilizado no vetor de byte
    public String toString() {

        return (" ## CONTA ## \n" +
                "ID: " + this.id + "\n" +
                "NOME: " + this.nome + "\n" +
                "EMAIL: " + this.email + "\n" +
                "NOME USUARIO: " + this.nome_usuario + "\n" +
                "SENHA: " + this.senha + "\n" +
                "CPF: " + this.cpf + "\n" +
                "CIDADE: " + this.cidade + "\n" +
                "TRANSFERENCIA REALIZADA: " + this.transferencia_realizada + "\n" +
                "SALDO CONTA: " + this.SaldoConta + "\n");
    }

    // get e set
    public int getID() {

        return this.id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getcpf() {
        return cpf;
    }

    public String getCidade() {
        return cidade;
    }
    public int getTransferencia_realizada() {

        return transferencia_realizada;
    }
    public float getSaldo_conta() {
        
        return SaldoConta;
    }
    

    
    public void setID(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public void setTransferencia_realizada(int transferencia_realizada) {
        this.transferencia_realizada = transferencia_realizada;
    }
    public void setSaldoConta(float saldoConta) {
        SaldoConta = saldoConta;
    }


}