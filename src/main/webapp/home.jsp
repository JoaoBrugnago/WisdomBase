<%@ page import="com.wisdom.models.Usuario" %>

<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
    if (usuario != null) {
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
            <link rel="stylesheet" href="static/css/home.css">
            <title>Home</title>
        </head>

        <body>
            <header class="home-header">
                <img src="static/img/wisdom.png" alt="WisdomBase Logo" class="home-logo">

                <nav class="home-nav">
                    <div class="home-itens-menu">
                        <span class="home-subtitle">Item 1</span>
                        <ul class="home-dropdown">
                            <li><a href="#">Saiba mais</a></li>
                            <li><a href="#">Outros projetos</a></li>
                            <li><a href="#">Sobre o autor</a></li>
                        </ul>
                    </div>

                    <div class="home-itens-menu">
                        <span class="home-subtitle">Item 2</span>
                        <ul class="home-dropdown">
                            <li><a href="#">Sobre</a></li>
                        </ul>
                    </div>

                    <div class="home-itens-menu">
                        <span class="home-subtitle">Item 3</span>
                        <ul class="home-dropdown">
                            <li><a href="#">Contato</a></li>
                        </ul>
                    </div>
                </nav>
            </header>

            <script src="static/js/home.js"></script>
        </body>
    </html>

<%
    } else {response.sendRedirect("login");}
%>
