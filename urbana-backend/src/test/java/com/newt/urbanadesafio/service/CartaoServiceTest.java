package com.newt.urbanadesafio.service;

import com.newt.urbanadesafio.dto.CartaoCreateDTO;
import com.newt.urbanadesafio.dto.CartaoDTO;
import com.newt.urbanadesafio.entity.Cartao;
import com.newt.urbanadesafio.entity.Usuario;
import com.newt.urbanadesafio.enums.TipoCartao;
import com.newt.urbanadesafio.repository.CartaoRepository;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @Test
    @DisplayName("Deve criar cartão para usuário existente")
    void deveCriarCartaoComSucesso() {
        // CENÁRIO
        Long usuarioId = 1L;

        // Dados de entrada
        CartaoCreateDTO dto = new CartaoCreateDTO();
        dto.setUsuarioId(usuarioId);
        dto.setNumeroCartao("12345");
        dto.setNome("Cartão Estudante");
        dto.setTipoCartao(TipoCartao.ESTUDANTE);

        // Usuário que o banco vai "encontrar"
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        // Cartão que o banco vai "salvar"
        Cartao cartaoSalvo = new Cartao();
        cartaoSalvo.setId(10L);
        cartaoSalvo.setNumeroCartao("12345");
        cartaoSalvo.setUsuario(usuario);
        cartaoSalvo.setTipoCartao(TipoCartao.ESTUDANTE);
        cartaoSalvo.setStatus(true);

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartaoSalvo);

        // AÇÃO
        CartaoDTO resultado = cartaoService.criar(dto);

        // VERIFICAÇÃO
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(12345L, resultado.getNumeroCartao());
        assertTrue(resultado.getStatus());
    }

    @Test
    @DisplayName("Deve falhar ao criar cartão sem usuário")
    void deveFalharAoCriarCartaoSemUsuario() {
        //CENÁRIO
        CartaoCreateDTO dto = new CartaoCreateDTO();
        dto.setUsuarioId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // AÇÃO E VERIFICAÇÃO
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cartaoService.criar(dto);
        });

        assertEquals("Usuário dono do cartão não encontrado!", ex.getMessage());
    }
}