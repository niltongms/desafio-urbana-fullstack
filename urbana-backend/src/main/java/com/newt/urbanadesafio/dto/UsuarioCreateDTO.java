package com.newt.urbanadesafio.dto;

import java.time.LocalDate;

// Esse DTO serve EXCLUSIVAMENTE para receber dados do formulário de cadastro
public class UsuarioCreateDTO {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private LocalDate dataNascimento;
    private String perfil;

    // Construtor vazio
    public UsuarioCreateDTO() {}

    // Getters e Setters
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
}