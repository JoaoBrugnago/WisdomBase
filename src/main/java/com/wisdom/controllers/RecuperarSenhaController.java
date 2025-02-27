package com.wisdom.controllers;

import com.wisdom.models.Usuario;
import com.wisdom.repositories.TokenRepository;
import com.wisdom.repositories.UsuarioRepository;
import com.wisdom.utils.EmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/recuperarSenha")
public class RecuperarSenhaController extends HttpServlet {
    private UsuarioRepository usuarioRepository;
    private TokenRepository tokenRepository;

    @Override
    public void init() {
        usuarioRepository = new UsuarioRepository();
        tokenRepository = new TokenRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        request.removeAttribute("errorMessage");

        RequestDispatcher dispatcher = request.getRequestDispatcher("/recuperarSenha.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao redirecionar a página.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();
        boolean temErro = false;
        String mensagem = "";

        if (!temErro) {
            if (!usuarioRepository.validarEmail(email)) {
                temErro = true;
                mensagem = "Este e-mail não existe.";
            }
        }
        if (!temErro) {
            if (tokenRepository.validarToken(token)) {
                temErro = true;
                mensagem = "Este token já existe.";
            }
        }
        if (!temErro) {
            Usuario usuario = usuarioRepository.recuperarUsuarioViaEmail(email);
            tokenRepository.cadastrarToken(token, "RECUPERARSENHA", usuario.getId());
            //-- Apenas teste, ver como parametrizar uma mensagem depois
            try {
                String assunto = "Recuperação de senha";
                String corpo = "<h1>Prezado(a) " + usuario.getNome() + ",</h1>";
                corpo += "<p>Segue link para recuperação de senha:</p>";
                corpo += "<p><a href='http://localhost:8081/WisdomBase/redefinirSenha?token=" + token + "'>Clique aqui para redefinir sua senha</a></p>";
                corpo += "<p>Se você não solicitou a recuperação de senha, ignore este e-mail.</p>";
                EmailService.enviarEmail(usuario.getEmail(), assunto, corpo);
            } catch (MessagingException e) {
                //-- Ver como tratar o retorno..
            }
        } else {
            request.setAttribute("errorMessage", mensagem);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/recuperarSenha.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao redirecionar a página.", e);
            }
        }
    }
}
