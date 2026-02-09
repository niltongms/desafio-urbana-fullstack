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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @Test
    @DisplayName("Deve criar cartão com número gerado automaticamente e saldo inicial")
    void deveCriarCartaoComSucesso() {
        // CENÁRIO
        Long usuarioId = 1L;
        CartaoCreateDTO dto = new CartaoCreateDTO();
        dto.setUsuarioId(usuarioId);
        dto.setNome("Meu Cartão");
        dto.setTipoCartao(TipoCartao.ESTUDANTE);
        dto.setSaldoInicial(new BigDecimal("50.00"));

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        // Mock para garantir que o gerador de número único passe na verificação do banco
        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuario));
        when(cartaoRepository.existsByNumeroCartao(anyString())).thenReturn(false);

        // Mock do salvamento: capturamos o que o service gerou e retornamos
        when(cartaoRepository.save(any(Cartao.class))).thenAnswer(invocation -> {
            Cartao c = invocation.getArgument(0);
            c.setId(10L); // Simula o ID gerado pelo banco
            return c;
        });

        // AÇÃO
        CartaoDTO resultado = cartaoService.criar(dto);

        // VERIFICAÇÃO
        assertNotNull(resultado);
        assertEquals(10L, resultado.getId());
        assertEquals(new BigDecimal("50.00"), resultado.getSaldo());
        assertTrue(resultado.getStatus());
        // Verifica se o prefixo do número gerado corresponde ao tipo ESTUDANTE (20)
        assertTrue(resultado.getNumeroCartao().startsWith("20"));
        verify(cartaoRepository, times(1)).save(any(Cartao.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar cartão para usuário inexistente")
    void deveFalharAoCriarCartaoSemUsuario() {
        CartaoCreateDTO dto = new CartaoCreateDTO();
        dto.setUsuarioId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cartaoService.criar(dto));
        assertEquals("Usuário dono do cartão não encontrado!", ex.getMessage());
    }

    @Test
    @DisplayName("Deve alterar status do cartão com sucesso")
    void deveAlterarStatusCartao() {
        // CENÁRIO
        Long cartaoId = 10L;
        Cartao cartao = new Cartao();
        cartao.setId(cartaoId);
        cartao.setStatus(true);
        cartao.setSaldo(BigDecimal.ZERO);
        cartao.setNumeroCartao("20123456");

        when(cartaoRepository.findById(cartaoId)).thenReturn(Optional.of(cartao));
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);

        // AÇÃO
        CartaoDTO resultado = cartaoService.alterarStatus(cartaoId, false);

        // VERIFICAÇÃO
        assertFalse(resultado.getStatus());
        verify(cartaoRepository).save(cartao);
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar deletar cartão que não existe")
    void deveFalharAoDeletarCartaoInexistente() {
        Long idInexistente = 500L;
        when(cartaoRepository.existsById(idInexistente)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cartaoService.deletar(idInexistente));
        assertEquals("Cartão não encontrado!", ex.getMessage());
        verify(cartaoRepository, never()).deleteById(anyLong());
    }
}