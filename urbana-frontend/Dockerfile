# Dockerfile
FROM node:20-alpine

# Instala Angular CLI globalmente
RUN npm install -g @angular/cli@17

WORKDIR /app

# Expõe a porta 4200
EXPOSE 4200

# Comando de inicialização com Host 0.0.0.0 (para acesso externo) e Poll (para hot-reload no Linux)
CMD ["ng", "serve", "--host", "0.0.0.0", "--poll", "2000"]