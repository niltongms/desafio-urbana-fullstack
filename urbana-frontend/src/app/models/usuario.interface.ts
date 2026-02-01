export interface Usuario {
  id?: number;        
  nome: string;
  email: string;
  cpf: string;
  dataNascimento: string; 
  perfil?: 'COMUM' | 'ESTUDANTE' | 'TRABALHADOR';
}