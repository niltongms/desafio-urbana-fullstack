import { Routes } from '@angular/router';
import { UsuarioListaComponent } from './components/usuario-lista/usuario-lista.component';

export const routes: Routes = [
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'usuarios', component: UsuarioListaComponent }
];