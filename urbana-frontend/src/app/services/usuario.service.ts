import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.interface';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  // Endereço do Backend Java
  private apiUrl = 'http://localhost:8080/usuarios'; 

  constructor(private http: HttpClient) { }

  // Método que vai lá no Java buscar a lista
  listarTodos(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }
}