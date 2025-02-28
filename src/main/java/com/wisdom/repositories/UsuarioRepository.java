package com.wisdom.repositories;

import com.wisdom.models.Usuario;
import com.wisdom.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
    public Usuario recuperarUsuarioViaEmailESenha(String email, String password) {
        String read = "select * from Usuarios where UsuarioEmail = ? and UsuarioSenha = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("UsuarioIdSequencia"),
                            rs.getString("UsuarioNome"),
                            rs.getString("UsuarioEmail"),
                            rs.getString("UsuarioSenha"),
                            Usuario.Status.valueOf(rs.getString("UsuarioStatus"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuário.", e);
        }
        return null;
    }

    public void cadastrarUsuario(String nome, String email, String senha) {
        String update = "insert into Usuarios (UsuarioNome, UsuarioEmail, UsuarioSenha, UsuarioStatus) values (?, ?, ?, 'ATIVO')";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, senha);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum usuário cadastrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário.", e);
        }
    }

    public Usuario recuperarUsuarioViaId(int id) {
        String read = "select * from Usuarios where UsuarioIdSequencia = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("UsuarioIdSequencia"),
                            rs.getString("UsuarioNome"),
                            rs.getString("UsuarioEmail"),
                            rs.getString("UsuarioSenha"),
                            Usuario.Status.valueOf(rs.getString("UsuarioStatus"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuário.", e);
        }
        return null;
    }

    public Usuario recuperarUsuarioViaEmail(String email) {
        String read = "select * from Usuarios where UsuarioEmail = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("UsuarioIdSequencia"),
                            rs.getString("UsuarioNome"),
                            rs.getString("UsuarioEmail"),
                            rs.getString("UsuarioSenha"),
                            Usuario.Status.valueOf(rs.getString("UsuarioStatus"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuário.", e);
        }
        return null;
    }

    public boolean validarEmail(String email) {
        String read = "select 1 from Usuarios where UsuarioEmail = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // Retorna true se encontrou o e-mail
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar email.", e);
        }
    }

    public void alterarSenhaViaId(int idUsuario, String senha) {
        String update = "update Usuarios set UsuarioSenha = ? where UsuarioIdSequencia = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, senha);
            pst.setInt(2, idUsuario);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Erro: Nenhuma linha foi atualizada. Verifique se o usuário existe.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao alterar senha do usuário.", e);
        }
    }
}
