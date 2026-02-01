package com.newt.urbanadesafio.service;

import com.newt.urbanadesafio.dto.UsuarioCreateDTO;
import com.newt.urbanadesafio.dto.UsuarioDTO;
import com.newt.urbanadesafio.entity.Usuario;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    // Injeção via Construtor
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioDTO::new) // Transforma Entidade em DTO
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO criar(UsuarioCreateDTO dto) { // Recebe o CreateDTO
        // Validação
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Já existe um usuário com este e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setCpf(dto.getCpf());
        usuario.setDataNascimento(dto.getDataNascimento());
        usuario.setPerfil(dto.getPerfil());

        usuario = usuarioRepository.save(usuario);

        // Retorna o DTO padrão (com ID e sem senha exposta)
        return new UsuarioDTO(usuario);
    }

    @Transactional
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());

        // Só troca a senha se o usuário mandou uma nova
        if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
            usuario.setSenha(dto.getSenha());
        }

        return new UsuarioDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        usuarioRepository.deleteById(id);
    }
}