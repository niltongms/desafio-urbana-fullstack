import { Routes } from '@angular/router';
import { UsuarioListaComponent } from './components/usuario-lista/usuario-lista.component';

import { UsuarioCadastroComponent } from './components/usuario-cadastro/usuario-cadastro.component';

export const routes: Routes = [
  { path: '', redirectTo: 'usuarios', pathMatch: 'full' },
  { path: 'usuarios', component: UsuarioListaComponent },
  { path: 'usuarios/novo', component: UsuarioCadastroComponent }
];