package com.wisdom.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/qualquerpagina") //-- Por enquanto não estou usando essa classe, pois ela gera muitos redirecionamentos no browser..
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String uri = httpRequest.getRequestURI();
        boolean usuarioLogado = (session != null && session.getAttribute("usuarioLogado") != null);
        boolean isLoginPage = uri.endsWith("login.jsp") || uri.endsWith("login");
        boolean isStaticResource = uri.startsWith(httpRequest.getContextPath() + "/static/");

        // Permitir acesso a recursos estáticos (CSS, JS, imagens)
        if (isStaticResource) {
            chain.doFilter(request, response);
            return;
        }

        // Se estiver logado e tentar acessar a página de login, redireciona para a home
        if (usuarioLogado && isLoginPage) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
            return;
        }

        // Se não estiver logado e tentar acessar uma página protegida, redireciona para login
        if (!usuarioLogado) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        // Caso contrário, segue normalmente
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
