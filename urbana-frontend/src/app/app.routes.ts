import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { UsuarioListaComponent } from './components/usuario-lista/usuario-lista.component';
import { UsuarioCadastroComponent } from './components/usuario-cadastro/usuario-cadastro.component';
import { HomeComponent } from './components/home/home.component';
import { SignupComponent } from './components/signup/signup.component';
import { MeusCartoesComponent } from './components/meus-cartoes/meus-cartoes.component'; 

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  
  
  { path: 'login', component: LoginComponent },
  { path: 'inscreva-se', component: SignupComponent },
  
  { path: 'home', component: HomeComponent },
  { path: 'meus-cartoes', component: MeusCartoesComponent }, 

  { path: 'usuarios', component: UsuarioListaComponent },
  { path: 'usuarios/novo', component: UsuarioCadastroComponent },
  { path: 'usuarios/editar/:id', component: UsuarioCadastroComponent },
  
 
  { path: '**', redirectTo: 'login' }
];