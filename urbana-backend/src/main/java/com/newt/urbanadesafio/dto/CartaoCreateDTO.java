package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.enums.TipoCartao;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CartaoCreateDTO {

    private String numeroCartao;

    @NotNull(message = "O nome do cartão é obrigatório")
    private String nome;

    @NotNull(message = "O tipo do cartão é obrigatório (COMUM, ESTUDANTE ou TRABALHADOR)")
    private TipoCartao tipoCartao;

    private BigDecimal saldoInicial;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    // Getters e Setters
    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }

    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}