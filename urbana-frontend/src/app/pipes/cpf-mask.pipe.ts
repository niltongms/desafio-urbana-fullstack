import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'cpfMask',
  standalone: true 
})
export class CpfMaskPipe implements PipeTransform {

  transform(value: string): string {
    // Se o valor for vazio ou nulo, retorna vazio
    if (!value) return '';

    // Remove tudo que não for número 
    const cpfLimpo = value.replace(/\D/g, '');

    // Se não tiver 11 dígitos, retorna o valor original
    if (cpfLimpo.length !== 11) {
      return value;
    }

    return cpfLimpo.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/,'***.***.$3-$4');
  }
}