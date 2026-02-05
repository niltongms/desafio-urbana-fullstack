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

  listarPorUsuario(usuarioId: number): Observable<Cartao[]> {
    return this.http.get<Cartao[]>(`${this.apiUrl}/usuario/${usuarioId}`);
  }

  solicitarCartao(usuarioId: number, tipo: string): Observable<Cartao> {
    const dados = {
      usuarioId: usuarioId,
      tipoCartao: tipo, 
      saldo: 0.0,
      status: true,     
      nome: 'Cartão ' + tipo
    };
    return this.http.post<Cartao>(this.apiUrl, dados);
  }

  recarregar(cartaoId: number, valor: number): Observable<Cartao> {
    const body = { valor: valor };
    return this.http.post<Cartao>(`${this.apiUrl}/${cartaoId}/recarga`, body);
  }

  cadastrar(cartao: Cartao): Observable<Cartao> {
    return this.http.post<Cartao>(this.apiUrl, cartao);
  }

  alterarStatus(id: number, ativo: boolean): Observable<Cartao> {
    return this.http.patch<Cartao>(`${this.apiUrl}/${id}/status?ativo=${ativo}`, {});
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}