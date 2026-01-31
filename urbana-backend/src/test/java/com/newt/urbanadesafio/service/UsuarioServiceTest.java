package com.newt.urbanadesafio.service;

import com.newt.urbanadesafio.dto.UsuarioCreateDTO;
import com.newt.urbanadesafio.dto.UsuarioDTO;
import com.newt.urbanadesafio.entity.Usuario;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock // Cria um "Repository de Mentira"
    private UsuarioRepository usuarioRepository;

    @InjectMocks // Injeta o repository de mentira dentro do Service verdadeiro
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        // CENÁRIO
        // Criar o DTO que simula o que vem do Controller
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNome("Teste da Silva");
        dto.setEmail("teste@email.com");
        dto.setSenha("123456");

        // Criar a Entidade que o Repository "retornaria" do banco
        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome("Teste da Silva");
        usuarioSalvo.setEmail("teste@email.com");
        usuarioSalvo.setSenha("123456");

        // Ensinar o Mockito: "Quando alguém chamar save(), retorne usuarioSalvo"
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        // AÇÃO
        UsuarioDTO resultado = usuarioService.criar(dto);

        //VERIFICAÇÃO
        // Verificar se o resultado é o esperado
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Teste da Silva", resultado.getNome());
        assertEquals("teste@email.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar ID inexistente")
    void deveLancarErroAoBuscarUsuarioInexistente() {
        //CENÁRIO
        Long idInexistente = 99L;


        when(usuarioRepository.findById(idInexistente)).thenReturn(java.util.Optional.empty());

        // AÇÃO e VERIFICAÇÃO
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(idInexistente);
        });

        // Verificar se a mensagem de erro é exatamente a que definimos no Service
        assertEquals("Usuário não encontrado!", exception.getMessage());
    }
}