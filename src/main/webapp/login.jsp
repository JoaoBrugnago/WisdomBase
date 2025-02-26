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
            <link rel="stylesheet" href="static/css/login.css">
            <title>Login</title>
        </head>

        <body>
            <div class="login-container">
                <img src="static/img/wisdom.png" alt="WisdomBase Logo" class="login-logo">
                <h1>Sign in with Wisdom.</h1>

                <% if (request.getParameter("cadastroSucesso") != null) { %>
                    <p class="login-success">Cadastro realizado com sucesso!</p>
                <% } %>

                <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="login-error"><%= request.getAttribute("errorMessage") %></p>
                <% } %>

                <form action="login" method="POST" class="login-form">
                    <div class="login-input-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="Digite seu e-mail">
                    </div>
                    <div class="login-input-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" required placeholder="Digite sua senha">
                    </div>
                    <button type="submit" class="login-btn-submit">Entrar</button>
                </form>

                <a href="cadastro" class="login-cadastro">Cadastre-se</a>
                <a href="recuperarSenha" class="login-recuperar">Recuperar senha</a>
            </div>
        </body>
    </html>

<%
    } else {
        response.sendRedirect("home");
        return;
    }
%>
