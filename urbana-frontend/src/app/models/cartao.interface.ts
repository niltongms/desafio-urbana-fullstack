export interface Cartao {
  id?: number;
  numeroCartao: string;
  nome: string;
  status: boolean;
  tipoCartao: 'COMUM' | 'ESTUDANTE' | 'TRABALHADOR';
  saldo: number;
  usuarioId: number;
}