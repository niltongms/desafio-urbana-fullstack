package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.entity.Cartao;
import com.newt.urbanadesafio.enums.TipoCartao;

public class CartaoDTO {
    private Long id;
    private Long numeroCartao;
    private String nome;
    private Boolean status;
    private TipoCartao tipoCartao;
    private Long usuarioId;

    public CartaoDTO() {}

    public CartaoDTO(Cartao cartao) {
        this.id = cartao.getId();
        this.numeroCartao = cartao.getNumeroCartao();
        this.nome = cartao.getNome();
        this.status = cartao.getStatus();
        this.tipoCartao = cartao.getTipoCartao();
        // Pega só o ID do dono
        if (cartao.getUsuario() != null) {
            this.usuarioId = cartao.getUsuario().getId();
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(Long numeroCartao) { this.numeroCartao = numeroCartao; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}