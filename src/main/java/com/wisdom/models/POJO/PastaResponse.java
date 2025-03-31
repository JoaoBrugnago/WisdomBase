package com.wisdom.models.POJO;

public class PastaResponse {
    private int id;
    private String tipo;
    private String nome;
    private boolean carregado;

    public PastaResponse(int id, String tipo, String nome, boolean carregado) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.carregado = carregado;
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

    public boolean isCarregado() {
        return carregado;
    }

    public void setCarregado(boolean carregado) {
        this.carregado = carregado;
    }
}
