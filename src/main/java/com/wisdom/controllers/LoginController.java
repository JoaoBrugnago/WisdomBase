package com.wisdom.controllers;

import com.wisdom.models.Usuario;
import com.wisdom.repositories.UsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UsuarioRepository usuarioRepository;

    @Override
    public void init() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.removeAttribute("errorMessage");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao redirecionar a página.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Usuario usuario = usuarioRepository.recuperarUsuario(email, senha);

        if (usuario != null) {
            request.getSession().setAttribute("usuarioLogado", usuario);
            try {
                response.sendRedirect("home");
            } catch (IOException e) {
                throw new RuntimeException("Erro ao redirecionar a página.", e);
            }

        } else {
            request.setAttribute("errorMessage", "Usuário ou senha incorretos.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao redirecionar a página.", e);
            }
        }
    }
}
