import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Cartao } from '../../models/cartao.interface';
import { CartaoService } from '../../services/cartao.service';
import { ToastrService } from 'ngx-toastr';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cartao-controle',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './cartao-controle.component.html',
  styleUrl: './cartao-controle.component.scss'
})
export class CartaoControleComponent implements OnChanges {
  // Recebe o ID do usuário selecionado na lista
  @Input() usuarioId: number | null = null;
  
  cartoes: Cartao[] = [];
  formCartao: FormGroup;

  constructor(
    private cartaoService: CartaoService,
    private fb: FormBuilder,
    private toastr: ToastrService
  ) {
    this.formCartao = this.fb.group({
      nome: ['', [Validators.required]],
      numeroCartao: ['', [Validators.required, Validators.pattern(/^\d+$/)]], 
      tipoCartao: ['COMUM', [Validators.required]],
      saldoInicial: [0]
    });
  }

  // Sempre que o usuarioId mudar, recarrega a lista
  ngOnChanges(changes: SimpleChanges): void {
    if (this.usuarioId) {
      this.carregarCartoes();
    }
  }

  carregarCartoes() {
    if (!this.usuarioId) return;
    
    this.cartaoService.listarPorUsuario(this.usuarioId).subscribe({
      next: (dados) => this.cartoes = dados,
      error: () => this.toastr.error('Erro ao carregar cartões.')
    });
  }

  salvar() {
    if (this.formCartao.valid && this.usuarioId) {
      const dados = {
        ...this.formCartao.value,
        usuarioId: this.usuarioId
      };

      this.cartaoService.cadastrar(dados).subscribe({
        next: (novoCartao) => {
          this.cartoes.push(novoCartao); 
          this.toastr.success('Cartão adicionado!');
          this.formCartao.reset({ tipoCartao: 'COMUM', saldoInicial: 0 }); 
        },
        error: (err) => this.toastr.error('Erro ao criar cartão. Verifique o número.')
      });
    }
  }

  excluir(id: number | undefined) {
    if (!id) return;
    
    Swal.fire({
      title: 'Excluir cartão?',
      text: 'O saldo será perdido permanentemente.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim, excluir',
      confirmButtonColor: '#d33'
    }).then((result) => {
      if (result.isConfirmed) {
        this.cartaoService.excluir(id).subscribe({
          next: () => {
            this.cartoes = this.cartoes.filter(c => c.id !== id);
            this.toastr.success('Cartão removido.');
          },
          error: () => this.toastr.error('Erro ao excluir.')
        });
      }
    });
  }
}