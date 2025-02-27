package com.wisdom.controllers;

import com.wisdom.models.Token;
import com.wisdom.models.Usuario;
import com.wisdom.repositories.TokenRepository;
import com.wisdom.repositories.UsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/redefinirSenha")
public class RedefinirSenhaController extends HttpServlet {

    private UsuarioRepository usuarioRepository;
    private TokenRepository tokenRepository;

    @Override
    public void init() {
        usuarioRepository = new UsuarioRepository();
        tokenRepository = new TokenRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.removeAttribute("message");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/redefinirSenha.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao redirecionar a página.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String senha = request.getParameter("senha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        boolean temErro = false;
        String message = "";

        if (!temErro) {
            if (token == null || token.isEmpty()) {
                temErro = true;
                message = "Token inválido.";
            }
        }
        if (!temErro) {
            if (senha == null || confirmarSenha == null || !senha.equals(confirmarSenha)) {
                temErro = true;
                message = "As senhas não coincidem.";
            }
        }
        if (!temErro) {
            boolean tokenValido = tokenRepository.validarTokenComDuracaoDeUmDia(token);
            if (!tokenValido) {
                temErro = true;
                message = "Token expirado.";
            }
        }
        if (!temErro) {
            Token tokenModel = tokenRepository.retornarToken(token);
            Usuario usuario = tokenModel.getUsuario();
            usuarioRepository.alterarSenha(usuario.getId(), senha);
            message = "Processo executado com sucesso.";
        }

        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/redefinirSenha.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao redirecionar a página.", e);
        }
    }
}
