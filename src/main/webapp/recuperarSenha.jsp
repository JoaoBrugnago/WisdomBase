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
            <link rel="stylesheet" href="static/css/recuperarSenha.css">
            <title>Recuperar senha</title>
        </head>

        <body>
            <div class="recuperarSenha-container">
                <img src="static/img/wisdom.png" alt="WisdomBase Logo" class="recuperarSenha-logo">
                <h1>Recuperar senha</h1>

                <% if (request.getParameter("cadastroSucesso") != null) { %>
                <p class="recuperarSenha-success">Procedimento executado, verifique seu e-mail!</p>
                <% } %>

                <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="recuperarSenha-error"><%= request.getAttribute("errorMessage") %></p>
                <% } %>

                <form action="recuperarSenha" method="POST" class="recuperarSenha-form">
                    <div class="recuperarSenha-input-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="Digite seu e-mail">
                    </div>
                    <button type="submit" class="recuperarSenha-btn-submit">Enviar</button>
                </form>

                <a href="login" class="recuperarSenha-login">Fazer login</a>
            </div>
        </body>
    </html>

<%
    } else {
        response.sendRedirect("home");
        return;
    }
%>
