package com.wisdom.models.POJO;

public class PastaResponse {
    private int id;
    private String tipo;
    private String nome;

    public PastaResponse(int id, String tipo, String nome) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
