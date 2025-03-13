package com.wisdom.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Arquivo {
    private int id;
    private String nome;
    private String tipo;
    private long tamanho;  // Tamanho do arquivo em bytes
    private byte[] conteudo;  // Conteúdo do arquivo em binário (BLOB)
    private String caminho;
    private int pastaId;
    private Timestamp dataCriacao;
    private int usuarioId;

    public Arquivo() {
    }

    public Arquivo(int id, String nome, String tipo, long tamanho, byte[] conteudo, String caminho, int pastaId, Timestamp dataCriacao, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.conteudo = conteudo;
        this.caminho = caminho;
        this.pastaId = pastaId;
        this.dataCriacao = dataCriacao;
        this.usuarioId = usuarioId;
    }

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTamanho() {
        return tamanho;
    }

    public void setTamanho(long tamanho) {
        this.tamanho = tamanho;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public int getPastaId() {
        return pastaId;
    }

    public void setPastaId(int pastaId) {
        this.pastaId = pastaId;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arquivo arquivo = (Arquivo) o;
        return id == arquivo.id &&
                tamanho == arquivo.tamanho &&
                pastaId == arquivo.pastaId &&
                usuarioId == arquivo.usuarioId &&
                Objects.equals(nome, arquivo.nome) &&
                Objects.equals(tipo, arquivo.tipo) &&
                Objects.equals(conteudo, arquivo.conteudo) &&
                Objects.equals(caminho, arquivo.caminho) &&
                Objects.equals(dataCriacao, arquivo.dataCriacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, tamanho, conteudo, caminho, pastaId, dataCriacao, usuarioId);
    }

    @Override
    public String toString() {
        return "Arquivo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", tamanho=" + tamanho +
                ", caminho='" + caminho + '\'' +
                ", pastaId=" + pastaId +
                ", dataCriacao=" + dataCriacao +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
