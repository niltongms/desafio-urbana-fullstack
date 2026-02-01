import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Cartao } from '../models/cartao.interface';

@Injectable({
  providedIn: 'root'
})
export class CartaoService {
  private apiUrl = 'http://localhost:8080/cartoes';

  constructor(private http: HttpClient) {}

  // Busca todos os cartões de um usuário
  listarPorUsuario(usuarioId: number): Observable<Cartao[]> {
    return this.http.get<Cartao[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  // Cria um cartão novo
  cadastrar(cartao: any): Observable<Cartao> {
    return this.http.post<Cartao>(this.apiUrl, cartao);
  }

  // Muda o status (Ativo/Inativo)
  alterarStatus(id: number, ativo: boolean): Observable<Cartao> {
    return this.http.patch<Cartao>(`${this.apiUrl}/${id}/status?ativo=${ativo}`, {});
  }

  // Remove o cartão
  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}