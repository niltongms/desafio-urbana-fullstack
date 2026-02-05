package com.newt.urbanadesafio.config;

import com.newt.urbanadesafio.entity.Usuario;
import com.newt.urbanadesafio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Configuration
public class AdminSetup implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // Dados do Admin
        String emailAdmin = "admin@urbana.com";
        String senhaAdmin = "adminurbana";

        // Verifica se já existe
        Optional<Usuario> adminExistente = usuarioRepository.findByEmail(emailAdmin);

        if (adminExistente.isPresent()) {
            Usuario admin = adminExistente.get();
            admin.setSenha(passwordEncoder.encode(senhaAdmin));
            admin.setPerfil("ADMIN");
            usuarioRepository.save(admin);

            System.out.println("🔄 SENHA DO ADMIN ATUALIZADA: " + senhaAdmin);

        } else {
            Usuario admin = new Usuario();
            admin.setNome("Administrador Urbana");
            admin.setEmail(emailAdmin);
            admin.setCpf("00000000000");
            admin.setDataNascimento(LocalDate.of(2000, 1, 1));

            admin.setSenha(passwordEncoder.encode(senhaAdmin));
            admin.setPerfil("ADMIN");

            usuarioRepository.save(admin);

            System.out.println("✅ USUÁRIO ADMIN CRIADO: " + emailAdmin);
        }
    }
}