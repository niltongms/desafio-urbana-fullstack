import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; 
import { FormsModule } from '@angular/forms';     
import { Router, RouterModule } from '@angular/router'; 
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true, 
  imports: [CommonModule, FormsModule, RouterModule], 
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginData = {
    email: '',
    senha: ''
  };

  mensagemErro: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    
    localStorage.clear(); 
  }

  irParaCadastro() {
    this.router.navigate(['/inscreva-se']);
  }

  entrar() {
    this.authService.login(this.loginData).subscribe({
      next: (response) => {
        
        const idUsuario = response?.id || response?.usuario?.id;
        
        if (idUsuario) {
          localStorage.setItem('usuario_id', idUsuario.toString());
        } else {
          console.warn('Backend não retornou ID do usuário no corpo da resposta.');
        }

        let perfil = this.authService.getPerfil() || '';
        perfil = perfil.trim().toUpperCase();

        console.log('Perfil detectado:', perfil); 

        if (perfil === 'ADMIN') {
            this.router.navigate(['/usuarios']);
        } else {
            this.router.navigate(['/home']);
        }
      },
      error: (erro) => {
        this.mensagemErro = 'Email ou senha inválidos!';
        console.error('Erro ao logar:', erro);
      }
    });
  }
}