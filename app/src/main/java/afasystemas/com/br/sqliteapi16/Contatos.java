package afasystemas.com.br.sqliteapi16;

import java.io.Serializable;

/**
 * Created by AndreFA on 03/11/2016.
 */

public class Contatos implements Serializable{

    private int id;
    private String nome;
    private String telefone;
    public Contatos(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return getNome()+" - "+getTelefone();
    }
}
