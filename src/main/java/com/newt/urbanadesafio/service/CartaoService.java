package com.newt.urbanadesafio.service;

import com.newt.urbanadesafio.dto.CartaoCreateDTO;
import com.newt.urbanadesafio.dto.CartaoDTO;
import com.newt.urbanadesafio.entity.Cartao;
import com.newt.urbanadesafio.entity.Usuario;
import com.newt.urbanadesafio.repository.CartaoRepository;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final UsuarioRepository usuarioRepository;

    public CartaoService(CartaoRepository cartaoRepository, UsuarioRepository usuarioRepository) {
        this.cartaoRepository = cartaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<CartaoDTO> listarTodos() {
        return cartaoRepository.findAll().stream()
                .map(CartaoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartaoDTO criar(CartaoCreateDTO dto) {
        // Regra: Não pode criar cartão pra um usuário que não existe
        Usuario dono = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário dono do cartão não encontrado!"));

        Cartao cartao = new Cartao();
        cartao.setNome(dto.getNome());
        cartao.setNumeroCartao(dto.getNumeroCartao());
        cartao.setTipoCartao(dto.getTipoCartao());
        cartao.setStatus(true); // Regra: Cartão já nasce Ativo
        cartao.setUsuario(dono);

        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    // Regra Extra: Ativar/Desativar cartão
    @Transactional
    public CartaoDTO alterarStatus(Long id, boolean ativo) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado!"));
        cartao.setStatus(ativo);
        return new CartaoDTO(cartaoRepository.save(cartao));
    }
}