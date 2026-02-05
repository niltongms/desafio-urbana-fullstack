import { Component, OnInit } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../services/auth.service'; 

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], 
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent implements OnInit {

  usuario = {
    nome: '',
    email: '',
    senha: '',
    cpf: '',
    dataNascimento: '',
    perfil: 'COMUM'
  };

  carregando = false;
  mensagemErro = '';
  mensagemSucesso = ''; 

  constructor(
    private http: HttpClient, 
    private router: Router,
    private authService: AuthService 
  ) {}

  ngOnInit(): void {
    console.log('SignupComponent carregado com sucesso!');
    
    
    this.authService.logout(false); 
  }

  cadastrar() {
    if (!this.usuario.nome || !this.usuario.email || !this.usuario.senha || !this.usuario.cpf) {
      this.mensagemErro = 'Por favor, preencha todos os campos obrigatórios.';
      return;
    }

    this.carregando = true;
    this.mensagemErro = '';
    this.mensagemSucesso = '';

    this.http.post('http://localhost:8080/usuarios', this.usuario).subscribe({
      next: () => {
        this.carregando = false;
        this.mensagemSucesso = 'Cadastro realizado com sucesso! Redirecionando para o login...';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      },
      error: (erro) => {
        console.error('Erro no cadastro:', erro);
        this.carregando = false;
        
        if (erro.status === 403 || erro.status === 401) {
          this.mensagemErro = 'Erro de comunicação com o servidor. Tente novamente em instantes.';
        } else if (erro.status === 400) {
          this.mensagemErro = 'Dados inválidos. Verifique se o CPF ou E-mail já estão em uso.';
        } else {
          this.mensagemErro = erro.error?.message || 'Erro ao realizar cadastro no sistema Urbana.';
        }
      }
    });
  }
}