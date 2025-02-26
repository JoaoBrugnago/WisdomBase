package com.wisdom.utils;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {

    private static final String USERNAME = "joao.psb20@gmail.com";
    private static final String PASSWORD = "sfkt tewt plqq gtxb";

    public static void enviarEmail(String destinatario, String assunto, String mensagemTexto) throws MessagingException {
        //-- Configurações do servidor de e-mail
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com"); //-- Servidor SMTP do Gmail
        properties.put("mail.smtp.port", "587"); //-- Porta SMTP
        properties.put("mail.smtp.auth", "true"); //-- Habilitar autenticação
        properties.put("mail.smtp.starttls.enable", "true"); //-- Habilitar TLS

        //-- Cria uma sessão com o servidor de e-mail
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD); // Usuário e senha
            }
        });

        //-- Cria a mensagem de e-mail
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USERNAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(assunto);
        message.setText(mensagemTexto);

        //-- Envia o e-mail
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            //-- Ver como tratar o retorno..
        }
    }
}
