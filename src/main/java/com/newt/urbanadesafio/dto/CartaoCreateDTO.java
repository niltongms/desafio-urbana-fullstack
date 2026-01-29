package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.enums.TipoCartao;

public class CartaoCreateDTO {
    private Long numeroCartao;
    private String nome;
    private TipoCartao tipoCartao;
    private Long usuarioId;

    // Getters e Setters
    public Long getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(Long numeroCartao) { this.numeroCartao = numeroCartao; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}