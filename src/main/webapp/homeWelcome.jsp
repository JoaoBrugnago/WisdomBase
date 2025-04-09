<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.wisdom.models.Usuario" %>

<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <link rel="icon" href="static/img/favicon.png">
        <link rel="stylesheet" href="static/css/homeWelcome.css">
        <title>Welcome</title>
    </head>

    <body>
        <div class="home-welcome">
            <h2>Seja bem vindo, <%= usuario.getNome() %>!</h2>
            <p>Use o menu lateral do <strong>WisdomBase</strong> ou faça uma pesquisa para explorar seus conteúdos.</p>
        </div>
    </body>
</html>