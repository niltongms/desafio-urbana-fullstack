package com.newt.urbanadesafio.dto;

import com.newt.urbanadesafio.entity.Usuario;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private LocalDate dataNascimento;
    private String perfil;
    private List<CartaoDTO> cartoes;

    public UsuarioDTO() {}

    // Construtor que converte Entidade -> DTO (Para devolver dados ao Frontend)
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        // Não devolvemos a senha aqui por segurança!
        this.cpf = usuario.getCpf();
        this.dataNascimento = usuario.getDataNascimento();
        this.perfil = usuario.getPerfil();

        if (usuario.getCartoes() != null) {
            this.cartoes = usuario.getCartoes().stream()
                    .map(CartaoDTO::new)
                    .collect(Collectors.toList());
        }
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public List<CartaoDTO> getCartoes() { return cartoes; }
    public void setCartoes(List<CartaoDTO> cartoes) { this.cartoes = cartoes; }
}