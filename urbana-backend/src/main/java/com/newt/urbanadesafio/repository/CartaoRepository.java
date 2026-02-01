package com.newt.urbanadesafio.repository;

import com.newt.urbanadesafio.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    // Busca cartões de um usuário específico
    List<Cartao> findByUsuarioId(Long usuarioId);

    // Verifica se já existe um cartão com este número
    boolean existsByNumeroCartao(String numeroCartao);
}