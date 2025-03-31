package com.wisdom.models.POJO;

import com.wisdom.models.Arquivo;
import com.wisdom.models.Pasta;

import java.util.List;

public class PastasArquivosResponse {
    private int id;
    private String tipo;
    private String nome;
    private boolean carregado;
    private List<PastaResponse> subpastas;
    private List<ArquivoResponse> arquivos;

    public PastasArquivosResponse() {}

    public PastasArquivosResponse(int id, String tipo, String nome, boolean carregado, List<PastaResponse> subpastas, List<ArquivoResponse> arquivos) {
        this.id = id;
        this.tipo = tipo;
        this.nome = nome;
        this.carregado = carregado;
        this.subpastas = subpastas;
        this.arquivos = arquivos;
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

    public List<PastaResponse> getSubpastas() {
        return subpastas;
    }

    public void setSubpastas(List<PastaResponse> subpastas) {
        this.subpastas = subpastas;
    }

    public List<ArquivoResponse> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoResponse> arquivos) {
        this.arquivos = arquivos;
    }
}
