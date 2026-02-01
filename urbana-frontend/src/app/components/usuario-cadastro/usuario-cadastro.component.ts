import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-usuario-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './usuario-cadastro.component.html',
  styleUrl: './usuario-cadastro.component.scss'
})
export class UsuarioCadastroComponent {
  cadastroForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router
  ) {
    this.cadastroForm = this.fb.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required]],
      dataNascimento: ['', [Validators.required]],
      perfil: ['', [Validators.required]]
    });
  }

  salvar() {
    if (this.cadastroForm.valid) {
      
      const valoresFormulario = this.cadastroForm.value;
      const usuarioParaEnviar = {
        ...valoresFormulario,
        cpf: valoresFormulario.cpf.replace(/\D/g, ''),
        senha: '123456' 
      };
      
      this.usuarioService.cadastrar(usuarioParaEnviar).subscribe({
        next: () => {
          alert('Usuário cadastrado com sucesso!');
          this.router.navigate(['/usuarios']);
        },
        error: (erro) => {
          console.error(erro);
          alert('Erro ao salvar. Verifique o console.');
        }
      });
    } else {
      alert('Preencha todos os campos corretamente!');
    }
  }
}