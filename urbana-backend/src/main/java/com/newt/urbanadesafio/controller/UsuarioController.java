package com.newt.urbanadesafio.controller;

import com.newt.urbanadesafio.dto.UsuarioCreateDTO;
import com.newt.urbanadesafio.dto.UsuarioDTO;
import com.newt.urbanadesafio.service.UsuarioService;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import com.newt.urbanadesafio.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/fix-admin")
    public String forcarAdmin() {
        try {
            Usuario admin = usuarioRepository.findByEmail("admin@urbana.com")
                    .orElse(new Usuario());

            // 2. Força os dados corretos
            admin.setNome("Admin Forçado");
            admin.setEmail("admin@urbana.com");
            admin.setPerfil("ADMIN");

            admin.setSenha(new BCryptPasswordEncoder().encode("adminurbana"));

            if (admin.getCpf() == null || admin.getCpf().isEmpty()) admin.setCpf("99999999999");
            if (admin.getDataNascimento() == null) admin.setDataNascimento(java.time.LocalDate.now());

            usuarioRepository.save(admin);

            return "SUCESSO! O usuário admin@urbana.com agora é ADMIN de verdade. Pode tentar logar.";
        } catch (Exception e) {
            return "ERRO AO TENTAR CONSERTAR: " + e.getMessage();
        }
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // Criar novo usuário
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto) {
        UsuarioDTO novoUsuario = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}