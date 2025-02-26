package com.wisdom.repositories;

import com.wisdom.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenRepository {
    public void cadastrarToken(String token, String chave, int usuarioId) {
        String update = "insert into Tokens (TokenConteudo, TokenTipoChave, UsuarioIdSequencia) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(update)) {

            pst.setString(1, token);
            pst.setString(2, chave);
            pst.setInt(3, usuarioId);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Nenhum token cadastrado.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar token.", e);
        }
    }

    public boolean validarToken(String token) {
        String read = "select 1 from Tokens where TokenConteudo = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(read)) {

            pst.setString(1, token);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar token.", e);
        }
    }
}
