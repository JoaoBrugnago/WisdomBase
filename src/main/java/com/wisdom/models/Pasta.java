package com.wisdom.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Pasta {
    private int id;
    private String nome;
    private String status;
    private Integer idPai;
    private Timestamp dataCriacao;
    private Timestamp dataUltimaModificacao;
    private int usuarioId;

    public Pasta() {
    }

    public Pasta(int id, String nome, String status, Integer idPai, Timestamp dataCriacao, Timestamp dataUltimaModificacao, int usuarioId) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.idPai = idPai;
        this.dataCriacao = dataCriacao;
        this.dataUltimaModificacao = dataUltimaModificacao;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdPai() {
        return idPai;
    }

    public void setIdPai(Integer idPai) {
        this.idPai = idPai;
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Timestamp getDataUltimaModificacao() {
        return dataUltimaModificacao;
    }

    public void setDataUltimaModificacao(Timestamp dataUltimaModificacao) {
        this.dataUltimaModificacao = dataUltimaModificacao;
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
        Pasta pasta = (Pasta) o;
        return id == pasta.id &&
                usuarioId == pasta.usuarioId &&
                Objects.equals(nome, pasta.nome) &&
                Objects.equals(status, pasta.status) &&
                Objects.equals(idPai, pasta.idPai) &&
                Objects.equals(dataCriacao, pasta.dataCriacao) &&
                Objects.equals(dataUltimaModificacao, pasta.dataUltimaModificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, status, idPai, dataCriacao, dataUltimaModificacao, usuarioId);
    }

    @Override
    public String toString() {
        return "Pasta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", status='" + status + '\'' +
                ", idPai=" + idPai +
                ", dataCriacao=" + dataCriacao +
                ", dataUltimaModificacao=" + dataUltimaModificacao +
                ", usuarioId=" + usuarioId +
                '}';
    }
}
