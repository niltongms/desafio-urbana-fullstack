import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router'; 

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/login';

  constructor(private http: HttpClient, private router: Router) { }

  login(loginData: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, loginData).pipe(
      tap(response => {
        
        const idNoCorpo = response?.id || response?.usuario?.id;
        if (idNoCorpo) {
          localStorage.setItem('usuario_id', idNoCorpo.toString());
        }

        if (response && response.token) {
          localStorage.setItem('token', response.token);
          this.decodificarToken(response.token);
        }
      })
    );
  }

  private decodificarToken(token: string): void {
    try {
      const payloadBase64 = token.split('.')[1];
      const payloadJson = atob(payloadBase64);
      const payload = JSON.parse(payloadJson);

      console.log('Payload do JWT:', payload);

      let idUsuario = payload.id || payload.usuarioId || payload.usuario_id || payload.uid;
      
      
      if (!idUsuario && payload.sub && !payload.sub.includes('@')) {
        idUsuario = payload.sub;
      }
      
      if (idUsuario) {
        
        localStorage.setItem('usuario_id', idUsuario.toString());
        console.log('ID numérico persistido com sucesso:', idUsuario);
      } else {
        
        console.warn('Alerta: ID numérico não localizado no Token JWT.');
      }

      if (payload.perfil) {
        localStorage.setItem('perfil', payload.perfil);
      }
      if (payload.nome) {
        localStorage.setItem('nome', payload.nome);
      }
      if (payload.sub) {
        localStorage.setItem('email', payload.sub); 
      }

    } catch (error) {
      console.error('Erro ao decodificar o token:', error);
    }
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getPerfil(): string | null {
    return localStorage.getItem('perfil');
  }

  getNome(): string | null {
    return localStorage.getItem('nome');
  }

  logout(navegar: boolean = true): void {
    localStorage.clear();
    if (navegar) {
      this.router.navigate(['/login']);
    }
  }

  estaLogado(): boolean {
    return !!this.getToken();
  }
}