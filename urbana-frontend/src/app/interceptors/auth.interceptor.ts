import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();

  const isPublicApi = req.url.includes('/login') || 
                      (req.url.includes('/usuarios') && req.method === 'POST');

  if (isPublicApi) {
    return next(req);
  }

  let authReq = req;
  if (token) {
    authReq = req.clone({
      setHeaders: { Authorization: `Bearer ${token}` }
    });
  }

  return next(authReq).pipe(
    catchError((erro) => {
      
      const isSignupPage = router.url.includes('/inscreva-se');

      if ((erro.status === 401 || erro.status === 403) && !isSignupPage) {
        console.warn('-> Acesso negado. Redirecionando para login pelo Interceptor.');
        authService.logout();
        router.navigate(['/login']);
      }
      return throwError(() => erro);
    })
  );
};