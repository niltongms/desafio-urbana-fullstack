import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink, ActivatedRoute } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-usuario-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './usuario-cadastro.component.html',
  styleUrl: './usuario-cadastro.component.scss'
})
export class UsuarioCadastroComponent implements OnInit {
  cadastroForm: FormGroup;
  idUsuario: number | null = null;
  isEdicao: boolean = false;

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router,
    private route: ActivatedRoute,
    private toastr: ToastrService
  ) {
    this.cadastroForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required]],
      dataNascimento: ['', [Validators.required]],
      perfil: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    
    if (id) {
      this.idUsuario = Number(id);
      this.isEdicao = true;
      this.carregarDadosUsuario(this.idUsuario);
    }
  }

  carregarDadosUsuario(id: number) {
    this.usuarioService.buscarPorId(id).subscribe({
      next: (usuario) => {
        this.cadastroForm.patchValue({
          nome: usuario.nome,
          email: usuario.email,
          cpf: usuario.cpf,
          dataNascimento: usuario.dataNascimento,
          perfil: usuario.perfil
        });
      },
      error: (erro) => {
        this.toastr.error('Erro ao carregar dados do usuário.', 'Erro');
        this.router.navigate(['/usuarios']);
      }
    });
  }

  salvar() {
    if (this.cadastroForm.valid) {
      const valoresFormulario = this.cadastroForm.value;

      const usuarioParaEnviar = {
        ...valoresFormulario,
        cpf: valoresFormulario.cpf.replace(/\D/g, '')
      };

      if (!this.isEdicao) {
        usuarioParaEnviar.senha = '123456';
      }

      if (this.isEdicao && this.idUsuario) {
        this.usuarioService.atualizar(this.idUsuario, usuarioParaEnviar).subscribe({
          next: () => {
            this.toastr.success('Usuário atualizado com sucesso!', 'Sucesso');
            this.router.navigate(['/usuarios']);
          },
          error: (erro) => {
            console.error(erro);
            this.toastr.error('Erro ao atualizar usuário.', 'Erro');
          }
        });
      } else {
        this.usuarioService.cadastrar(usuarioParaEnviar).subscribe({
          next: () => {
            this.toastr.success('Usuário cadastrado com sucesso!', 'Sucesso');
            this.router.navigate(['/usuarios']);
          },
          error: (erro) => {
            console.error(erro);
            const msg = erro.error?.message || 'Erro ao realizar cadastro.';
            this.toastr.error(msg, 'Erro');
          }
        });
      }

    } else {
      this.toastr.warning('Preencha todos os campos corretamente.', 'Atenção');
      this.cadastroForm.markAllAsTouched();
    }
  }
}