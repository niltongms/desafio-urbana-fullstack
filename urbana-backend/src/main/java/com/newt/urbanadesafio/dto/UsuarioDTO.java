package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.entity.Usuario;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private List<CartaoDTO> cartoes; // Lista convertida, não a Entidade pura

    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        // Converte a lista de Cartões Entity para Cartões DTO (se houver)
        if (usuario.getCartoes() != null) {
            this.cartoes = usuario.getCartoes().stream()
                    .map(CartaoDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<CartaoDTO> getCartoes() { return cartoes; }
    public void setCartoes(List<CartaoDTO> cartoes) { this.cartoes = cartoes; }
}