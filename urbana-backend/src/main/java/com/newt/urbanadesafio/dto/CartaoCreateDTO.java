package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.enums.TipoCartao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CartaoCreateDTO {

    @NotNull(message = "O número do cartão é obrigatório")
    @Positive(message = "O número do cartão deve ser positivo")
    private Long numeroCartao;

    @NotNull(message = "O nome do cartão é obrigatório")
    private String nome;

    @NotNull(message = "O tipo do cartão é obrigatório (COMUM, ESTUDANTE ou TRABALHADOR)")
    private TipoCartao tipoCartao;

    @NotNull(message = "O ID do usuário é obrigatório")
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