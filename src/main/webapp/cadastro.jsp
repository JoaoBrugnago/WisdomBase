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
            <link rel="stylesheet" href="static/css/cadastro.css">
            <title>Cadastrar-se</title>
        </head>

        <body>
            <div class="cadastro-container">
                <img src="static/img/wisdom.png" alt="WisdomBase Logo" class="cadastro-logo">
                <h1>Cadastrar-se</h1>

                <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="cadastro-error"><%= request.getAttribute("errorMessage") %></p>
                <% } %>

                <form action="cadastro" method="POST" class="cadastro-form">
                    <div class="cadastro-input-group">
                        <label for="nome">Nome</label>
                        <input type="text" id="nome" name="nome" required placeholder="Digite seu nome">
                    </div>
                    <div class="cadastro-input-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required placeholder="Digite seu e-mail">
                    </div>
                    <div class="cadastro-input-group">
                        <label for="senha">Senha</label>
                        <input type="password" id="senha" name="senha" required placeholder="Digite sua senha">
                    </div>
                    <button type="submit" class="cadastro-btn-submit">Cadastrar</button>
                </form>

                <a href="login" class="cadastro-login">Fazer login</a>
            </div>
        </body>
    </html>

<%
    } else {
        response.sendRedirect("home");
        return;
    }
%>
