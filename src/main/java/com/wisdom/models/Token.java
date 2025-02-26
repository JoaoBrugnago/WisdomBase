package com.wisdom.models;

import java.sql.Timestamp;

public class Token {
    private String token;
    private String chave;
    private Usuario usuario;
    private Timestamp dataCriacao;

    public Token() {};

    public Token(String token, String chave, Usuario usuario, Timestamp dataCriacao) {
        this.token = token;
        this.chave = chave;
        this.usuario = usuario;
        this.dataCriacao = dataCriacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", chave='" + chave + '\'' +
                ", usuario=" + usuario +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
