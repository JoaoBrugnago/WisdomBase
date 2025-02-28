<%@ page import="com.wisdom.models.Usuario" %>

<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
    if (usuario == null) {
%>

    <!DOCTYPE html>
    <html lang="pt-br">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport"
                  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
            <meta http-equiv="X-UA-Compatible" content="ie=edge">
            <link rel="icon" href="static/img/favicon.png">
            <link rel="stylesheet" href="static/css/reset.css">
            <link rel="stylesheet" href="static/css/redefinirSenha.css">
            <title>Redefinir senha</title>
        </head>

        <body>
            <div class="redefinirSenha-container">
                <img src="static/img/wisdom.png" alt="WisdomBase Logo" class="redefinirSenha-logo">
                <h1>Redefinir senha.</h1>

                <% if (request.getParameter("cadastroSucesso") != null) { %>
                <p class="redefinirSenha-success">Procedimento realizado com sucesso!</p>
                <% } %>

                <% if (request.getAttribute("message") != null) { %>
                <p class="redefinirSenha-message"><%= request.getAttribute("message") %></p>
                <% } %>

                <form action="redefinirSenha" method="POST" class="redefinirSenha-form">
                    <div class="redefinirSenha-input-group">
                        <label for="senha">Nova Senha</label>
                        <input type="password" id="senha" name="senha" required placeholder="Digite uma nova senha">
                    </div>
                    <div class="redefinirSenha-input-group">
                        <label for="confirmarSenha">Confirme a Senha</label>
                        <input type="password" id="confirmarSenha" name="confirmarSenha" required placeholder="Confirme sua senha">
                    </div>

                    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

                    <button type="submit" class="redefinirSenha-btn-submit">Redefinir</button>
                </form>

                <a href="login" class="redefinirSenha-login">Login</a>
            </div>

            <script src="static/js/redefinirSenha.js"></script>
        </body>
    </html>

<%
    } else {
        response.sendRedirect("home");
        return;
    }
%>
