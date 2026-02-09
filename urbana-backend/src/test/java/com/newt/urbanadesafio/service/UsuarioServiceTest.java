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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deve criar um usuário com sucesso e senha criptografada")
    void deveCriarUsuarioComSucesso() {
        // CENÁRIO
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setNome("Elenilton Gomes");
        dto.setEmail("elenilton@email.com");
        dto.setSenha("senha123");
        dto.setCpf("123.456.789-00");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome(dto.getNome());
        usuarioSalvo.setEmail(dto.getEmail());
        usuarioSalvo.setSenha("senha_criptografada");

        // Mock das validações de unicidade
        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.existsByCpf(dto.getCpf())).thenReturn(false);

        // Mock da criptografia e salvamento
        when(passwordEncoder.encode(anyString())).thenReturn("senha_criptografada");
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        // AÇÃO
        UsuarioDTO resultado = usuarioService.criar(dto);

        // VERIFICAÇÃO
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("elenilton@email.com", resultado.getEmail());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar erro quando e-mail já existe")
    void deveFalharEmailDuplicado() {
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setEmail("duplicado@email.com");

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Usuario()));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.criar(dto));
        assertEquals("Já existe um usuário com este e-mail.", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro quando CPF já existe")
    void deveFalharCpfDuplicado() {
        UsuarioCreateDTO dto = new UsuarioCreateDTO();
        dto.setEmail("novo@email.com");
        dto.setCpf("000.000.000-00");

        when(usuarioRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.existsByCpf(dto.getCpf())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> usuarioService.criar(dto));
        assertEquals("Já existe um usuário cadastrado com este CPF!", ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar ID inexistente")
    void deveLancarErroAoBuscarUsuarioInexistente() {
        Long idInexistente = 99L;
        when(usuarioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(idInexistente));
        assertEquals("Usuário não encontrado!", exception.getMessage());
    }

    @Test
    @DisplayName("Deve deletar usuário com sucesso")
    void deveDeletarUsuario() {
        Long id = 1L;
        when(usuarioRepository.existsById(id)).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById(id);

        assertDoesNotThrow(() -> usuarioService.deletar(id));
        verify(usuarioRepository, times(1)).deleteById(id);
    }
}