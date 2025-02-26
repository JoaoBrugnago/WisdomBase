<%@ page import="com.wisdom.models.Usuario" %>

<%
    Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
    if (usuario != null) {
%>
    <h1>Bem-vindo, <%= usuario.getNome() %>!</h1>
    <!-- Outras funcionalidades da home -->
<%
    } else {response.sendRedirect("login");}
%>
