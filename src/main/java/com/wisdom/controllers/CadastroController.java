package com.wisdom.controllers;

import com.wisdom.repositories.UsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cadastro")
public class CadastroController extends HttpServlet {

    private UsuarioRepository usuarioRepository;

    @Override
    public void init() {
        usuarioRepository = new UsuarioRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.removeAttribute("errorMessage");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao redirecionar a página.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        boolean existeEmail = usuarioRepository.validarEmail(email);
        if (!existeEmail) {
            usuarioRepository.cadastrarUsuario(nome, email, senha);
            response.sendRedirect("login.jsp?cadastroSucesso=true");
        } else {
            request.setAttribute("errorMessage", "Este e-mail já está em uso.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastro.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao redirecionar a página.", e);
            }
        }
    }
}
