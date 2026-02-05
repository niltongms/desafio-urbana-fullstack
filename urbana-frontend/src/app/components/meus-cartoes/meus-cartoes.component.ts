import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CartaoService } from '../../services/cartao.service';
import { AuthService } from '../../services/auth.service';   
import { Cartao } from '../../models/cartao.interface';      
import Swal from 'sweetalert2'; 

@Component({
  selector: 'app-meus-cartoes',
  standalone: true,
  imports: [CommonModule, RouterModule], 
  templateUrl: './meus-cartoes.component.html',
  styleUrl: './meus-cartoes.component.scss'
})
export class MeusCartoesComponent implements OnInit {

  cartoes: Cartao[] = []; 
  carregando: boolean = false;

  constructor(
    private cartaoService: CartaoService,
    public authService: AuthService 
  ) {}

  ngOnInit(): void {
    this.carregarCartoes();
  }

  carregarCartoes(): void {
    this.carregando = true;
    const usuarioIdRaw = localStorage.getItem('usuario_id');
    const usuarioId = Number(usuarioIdRaw);

    
    if (usuarioIdRaw && !isNaN(usuarioId) && usuarioId > 0) {
      this.cartaoService.listarPorUsuario(usuarioId).subscribe({
        next: (dados) => {
          this.cartoes = dados;
          this.carregando = false;
        },
        error: () => {
          this.carregando = false;
          Swal.fire('Erro', 'Não foi possível carregar seus cartões.', 'error');
        }
      });
    } else {
      this.carregando = false;
    }
  }

  solicitarNovoCartao(): void {
    Swal.fire({
      title: 'Solicitar Novo Cartão?',
      text: "Um novo cartão comum será vinculado à sua conta.",
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#0d6efd',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Sim, solicitar!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        const usuarioId = Number(localStorage.getItem('usuario_id'));
        
        this.cartaoService.solicitarCartao(usuarioId, 'COMUM').subscribe({
          next: (novoCartao) => {
            this.cartoes.push(novoCartao); 
            Swal.fire('Sucesso!', 'Seu novo cartão foi gerado.', 'success');
          },
          error: () => {
            Swal.fire('Erro', 'Você já possui um cartão ativo ou houve uma falha no servidor.', 'error');
          }
        });
      }
    });
  }

  async fazerRecarga(cartao: Cartao): Promise<void> {
    const num = cartao.numeroCartao || cartao.numero || '****';
    const finalCartao = num.slice(-4);
    
    const { value: valorStr } = await Swal.fire({
      title: `Recarga - Final ${finalCartao}`,
      input: 'number',
      inputLabel: 'Informe o valor da recarga',
      inputPlaceholder: 'Ex: 50.00',
      showCancelButton: true,
      confirmButtonText: 'Confirmar Recarga',
      cancelButtonText: 'Cancelar',
      inputAttributes: {
        step: '0.01'
      },
      inputValidator: (value) => {
        if (!value || parseFloat(value) <= 0) {
          return 'Por favor, insira um valor positivo!';
        }
        return null;
      }
    });

    if (valorStr && cartao.id) {
      const valor = parseFloat(valorStr);
      this.cartaoService.recarregar(cartao.id, valor).subscribe({
        next: (cartaoAtualizado) => {
          const index = this.cartoes.findIndex(c => c.id === cartao.id);
          if (index !== -1) {
            this.cartoes[index] = cartaoAtualizado;
          }
          Swal.fire({
            icon: 'success',
            title: 'Recarga realizada!',
            text: `R$ ${valor.toFixed(2)} adicionados com sucesso.`,
            timer: 2000,
            showConfirmButton: false
          });
        },
        error: () => {
          Swal.fire('Erro', 'Falha ao processar a recarga.', 'error');
        }
      });
    }
  }
}