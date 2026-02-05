import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common'; 
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink], 
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  nomeUsuario: string = '';
  perfilUsuario: string = '';

  constructor(
    public authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.nomeUsuario = this.authService.getNome() || 'Visitante';
    this.perfilUsuario = this.authService.getPerfil() || 'Indefinido';
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}