package com.newt.urbanadesafio.controller;

import com.newt.urbanadesafio.dto.CartaoCreateDTO;
import com.newt.urbanadesafio.dto.CartaoDTO;
import com.newt.urbanadesafio.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    // Listar todos os cartões
    @GetMapping
    public ResponseEntity<List<CartaoDTO>> listarTodos() {
        return ResponseEntity.ok(cartaoService.listarTodos());
    }

    // Criar cartão
    @PostMapping
    public ResponseEntity<CartaoDTO> criar(@Valid @RequestBody CartaoCreateDTO dto) {
        CartaoDTO novoCartao = cartaoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCartao);
    }

    //  Ativar/Desativar cartão
    @PatchMapping("/{id}/status")
    public ResponseEntity<CartaoDTO> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        return ResponseEntity.ok(cartaoService.alterarStatus(id, ativo));
    }

    // Deletar cartão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cartaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}