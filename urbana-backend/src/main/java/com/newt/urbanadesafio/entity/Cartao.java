package com.newt.urbanadesafio.entity;

import com.newt.urbanadesafio.enums.TipoCartao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal; // Importante para dinheiro

@Entity
@Table(name = "tb_cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroCartao; // String para aceitar zeros e evitar conta matemática

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Boolean status; // true = Ativo

    @Column(nullable = false)
    private BigDecimal saldo; // O NOVO CAMPO DE DINHEIRO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCartao tipoCartao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore // Essencial para o Front não travar num loop
    private Usuario usuario;

    public Cartao() {}

    public Cartao(String numeroCartao, String nome, Boolean status, BigDecimal saldo, TipoCartao tipoCartao, Usuario usuario) {
        this.numeroCartao = numeroCartao;
        this.nome = nome;
        this.status = status;
        this.saldo = saldo;
        this.tipoCartao = tipoCartao;
        this.usuario = usuario;
    }

    // --- GETTERS E SETTERS OBRIGATÓRIOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }

    public TipoCartao getTipoCartao() { return tipoCartao; }
    public void setTipoCartao(TipoCartao tipoCartao) { this.tipoCartao = tipoCartao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}