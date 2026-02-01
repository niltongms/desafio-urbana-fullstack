import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { Usuario } from '../../models/usuario.interface';
import { CpfMaskPipe } from '../../pipes/cpf-mask.pipe';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

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

  constructor(
    private usuarioService: UsuarioService,
    private toastr: ToastrService
  ) {}

  ngOnInit(): void {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.usuarioService.listarTodos().subscribe({
      next: (dados) => {
        this.usuarios = dados;},
      error: (erro) => {
        console.error('Erro:', erro);
        this.toastr.error('Erro ao carregar lista de usuários.', 'Erro de Conexão');
      }
    });
  }

  excluirUsuario(id: number | undefined, nome: string) {
    if (!id) return;

    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja realmente excluir o usuário "${nome}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33', 
      cancelButtonColor: '#3085d6', 
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      
      if (result.isConfirmed) {
        
        this.usuarioService.excluir(id).subscribe({
          next: () => {
            this.usuarios = this.usuarios.filter(u => u.id !== id);
            this.toastr.success('Usuário excluído com sucesso!', 'Feito!');
          },
          error: (erro) => {
            console.error('Erro ao excluir:', erro);
            this.toastr.error('Não foi possível excluir o usuário. Verifique vínculos.', 'Erro');
          }
        });
      }
    });
  }
}