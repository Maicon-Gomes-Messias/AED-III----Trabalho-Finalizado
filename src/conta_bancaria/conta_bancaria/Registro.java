package conta_bancaria;

import java.io.IOException;

public interface Registro {
    public int getID();
    public void setID(int ID);
    public String getNome_usuario();
    public void setNome_usuario(String nome_usuario);
    public byte[] toByteArray() throws IOException;
    public void fromByteArray(byte[] ba) throws IOException;
}