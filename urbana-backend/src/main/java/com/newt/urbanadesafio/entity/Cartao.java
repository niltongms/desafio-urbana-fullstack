package com.newt.urbanadesafio.entity;

import com.newt.urbanadesafio.enums.TipoCartao;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long numeroCartao;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Boolean status; // true = Ativo, false = Inativo

    @Enumerated(EnumType.STRING) // Salva "COMUM" no banco (texto), não números
    @Column(nullable = false)
    private TipoCartao tipoCartao;

    // Muitos cartões pertencem a um usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cartao() {}

    public Cartao(Long numeroCartao, String nome, Boolean status, TipoCartao tipoCartao, Usuario usuario) {
        this.numeroCartao = numeroCartao;
        this.nome = nome;
        this.status = status;
        this.tipoCartao = tipoCartao;
        this.usuario = usuario;
    }

    // --- GETTERS E SETTERS ---
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
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}