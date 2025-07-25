<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                    <div class="home-menu">
                        <div class="home-itens-menu">
                            <button class="home-subtitle">Opcão 1</button>
                            <ul class="home-dropdown">
                                <li><a href="#">Saiba mais</a></li>
                                <li><a href="#">Outros projetos</a></li>
                                <li><a href="#">Sobre o autor</a></li>
                            </ul>
                        </div>

                        <div class="home-itens-menu">
                            <button class="home-subtitle">Opcão 2</button>
                            <ul class="home-dropdown">
                                <li><a href="#">Sobre</a></li>
                            </ul>
                        </div>

                        <div class="home-itens-menu">
                            <button class="home-subtitle">Opcão 3</button>
                            <ul class="home-dropdown">
                                <li><a href="#">Contato</a></li>
                            </ul>
                        </div>
                    </div>
                </nav>
            </header>

            <div class="breadcrumb-container">
                <!-- Aqui será carregado o breadcrumb -->
            </div>

            <div class="home-container">
                <aside class="home-sidebar">
                    <!-- A estrutura de árvore das pastas e arquivos serão carregados aqui -->
                </aside>

                <main class="home-content">
                    <div class="search-container">
                        <input type="text" id="search-input" placeholder="Pesquisar pastas e arquivos">
                    </div>

                    <div id="content">

                        <%
                            String pagina = request.getAttribute("pagina");

                            if (pagina == null || pagina.isEmpty()) {
                                pagina = "homeWelcome.jsp";
                            } else {
                                pagina = pagina + ".jsp";
                            }
                        %>

                        <jsp:include page="<%= pagina %>" />

                    </div>
                </main>
            </div>

            <script src="static/js/ajustarAlturaSidebar.js"></script>
            <script src="static/js/home.js"></script>
        </body>
    </html>

<%
    } else {response.sendRedirect("login");}
%>
