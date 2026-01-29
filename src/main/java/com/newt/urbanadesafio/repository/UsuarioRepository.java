package com.newt.urbanadesafio.repository;

import com.newt.urbanadesafio.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //  buscar email
    Usuario findByEmail(String email);
}