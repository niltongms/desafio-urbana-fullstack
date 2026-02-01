import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario.interface';
import { CpfMaskPipe } from '../../pipes/cpf-mask.pipe';

@Component({
  selector: 'app-usuario-lista',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    CpfMaskPipe
  ],
  templateUrl: './usuario-lista.component.html',
  styleUrl: './usuario-lista.component.scss'
})
export class UsuarioListaComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.usuarioService.listarTodos().subscribe({
      next: (dados) => {
        this.usuarios = dados;
        console.log('Sucesso! Dados recebidos:', dados);
      },
      error: (erro) => {
        console.error('Erro:', erro);
        alert('Erro ao buscar usuários. Verifique se o Backend Java está rodando!');
      }
    });
  }
}